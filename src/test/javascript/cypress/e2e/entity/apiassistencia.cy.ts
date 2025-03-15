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

describe('Apiassistencia e2e test', () => {
  const apiassistenciaPageUrl = '/apiassistencia';
  const apiassistenciaPageUrlPattern = new RegExp('/apiassistencia(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const apiassistenciaSample = {};

  let apiassistencia;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/apiassistencias+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/apiassistencias').as('postEntityRequest');
    cy.intercept('DELETE', '/api/apiassistencias/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (apiassistencia) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/apiassistencias/${apiassistencia.id}`,
      }).then(() => {
        apiassistencia = undefined;
      });
    }
  });

  it('Apiassistencias menu should load Apiassistencias page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('apiassistencia');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Apiassistencia').should('exist');
    cy.url().should('match', apiassistenciaPageUrlPattern);
  });

  describe('Apiassistencia page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(apiassistenciaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Apiassistencia page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/apiassistencia/new$'));
        cy.getEntityCreateUpdateHeading('Apiassistencia');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiassistenciaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/apiassistencias',
          body: apiassistenciaSample,
        }).then(({ body }) => {
          apiassistencia = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/apiassistencias+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/apiassistencias?page=0&size=20>; rel="last",<http://localhost/api/apiassistencias?page=0&size=20>; rel="first"',
              },
              body: [apiassistencia],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(apiassistenciaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Apiassistencia page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('apiassistencia');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiassistenciaPageUrlPattern);
      });

      it('edit button click should load edit Apiassistencia page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Apiassistencia');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiassistenciaPageUrlPattern);
      });

      it('edit button click should load edit Apiassistencia page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Apiassistencia');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiassistenciaPageUrlPattern);
      });

      it('last delete button click should delete instance of Apiassistencia', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('apiassistencia').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiassistenciaPageUrlPattern);

        apiassistencia = undefined;
      });
    });
  });

  describe('new Apiassistencia page', () => {
    beforeEach(() => {
      cy.visit(`${apiassistenciaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Apiassistencia');
    });

    it('should create an instance of Apiassistencia', () => {
      cy.get(`[data-cy="filtro"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="filtro"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="dadosassistencia"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="dadosassistencia"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="chave"]`).type('excepting given');
      cy.get(`[data-cy="chave"]`).should('have.value', 'excepting given');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        apiassistencia = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', apiassistenciaPageUrlPattern);
    });
  });
});
