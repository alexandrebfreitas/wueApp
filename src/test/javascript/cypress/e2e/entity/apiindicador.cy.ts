import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('Apiindicador e2e test', () => {
  const apiindicadorPageUrl = '/apiindicador';
  const apiindicadorPageUrlPattern = new RegExp('/apiindicador(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const apiindicadorSample = {};

  let apiindicador;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/apiindicadors+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/apiindicadors').as('postEntityRequest');
    cy.intercept('DELETE', '/api/apiindicadors/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (apiindicador) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/apiindicadors/${apiindicador.id}`,
      }).then(() => {
        apiindicador = undefined;
      });
    }
  });

  it('Apiindicadors menu should load Apiindicadors page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('apiindicador');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Apiindicador').should('exist');
    cy.url().should('match', apiindicadorPageUrlPattern);
  });

  describe('Apiindicador page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(apiindicadorPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Apiindicador page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/apiindicador/new$'));
        cy.getEntityCreateUpdateHeading('Apiindicador');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiindicadorPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/apiindicadors',
          body: apiindicadorSample,
        }).then(({ body }) => {
          apiindicador = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/apiindicadors+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/apiindicadors?page=0&size=20>; rel="last",<http://localhost/api/apiindicadors?page=0&size=20>; rel="first"',
              },
              body: [apiindicador],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(apiindicadorPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Apiindicador page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('apiindicador');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiindicadorPageUrlPattern);
      });

      it('edit button click should load edit Apiindicador page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Apiindicador');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiindicadorPageUrlPattern);
      });

      it('edit button click should load edit Apiindicador page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Apiindicador');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiindicadorPageUrlPattern);
      });

      it('last delete button click should delete instance of Apiindicador', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('apiindicador').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiindicadorPageUrlPattern);

        apiindicador = undefined;
      });
    });
  });

  describe('new Apiindicador page', () => {
    beforeEach(() => {
      cy.visit(`${apiindicadorPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Apiindicador');
    });

    it('should create an instance of Apiindicador', () => {
      cy.get(`[data-cy="filtro"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="filtro"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="dadosindicador"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="dadosindicador"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="chave"]`).type('train');
      cy.get(`[data-cy="chave"]`).should('have.value', 'train');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        apiindicador = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', apiindicadorPageUrlPattern);
    });
  });
});
