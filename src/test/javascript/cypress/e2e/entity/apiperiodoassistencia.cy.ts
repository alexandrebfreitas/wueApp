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

describe('Apiperiodoassistencia e2e test', () => {
  const apiperiodoassistenciaPageUrl = '/apiperiodoassistencia';
  const apiperiodoassistenciaPageUrlPattern = new RegExp('/apiperiodoassistencia(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const apiperiodoassistenciaSample = {};

  let apiperiodoassistencia;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/apiperiodoassistencias+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/apiperiodoassistencias').as('postEntityRequest');
    cy.intercept('DELETE', '/api/apiperiodoassistencias/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (apiperiodoassistencia) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/apiperiodoassistencias/${apiperiodoassistencia.id}`,
      }).then(() => {
        apiperiodoassistencia = undefined;
      });
    }
  });

  it('Apiperiodoassistencias menu should load Apiperiodoassistencias page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('apiperiodoassistencia');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Apiperiodoassistencia').should('exist');
    cy.url().should('match', apiperiodoassistenciaPageUrlPattern);
  });

  describe('Apiperiodoassistencia page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(apiperiodoassistenciaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Apiperiodoassistencia page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/apiperiodoassistencia/new$'));
        cy.getEntityCreateUpdateHeading('Apiperiodoassistencia');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiperiodoassistenciaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/apiperiodoassistencias',
          body: apiperiodoassistenciaSample,
        }).then(({ body }) => {
          apiperiodoassistencia = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/apiperiodoassistencias+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/apiperiodoassistencias?page=0&size=20>; rel="last",<http://localhost/api/apiperiodoassistencias?page=0&size=20>; rel="first"',
              },
              body: [apiperiodoassistencia],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(apiperiodoassistenciaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Apiperiodoassistencia page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('apiperiodoassistencia');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiperiodoassistenciaPageUrlPattern);
      });

      it('edit button click should load edit Apiperiodoassistencia page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Apiperiodoassistencia');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiperiodoassistenciaPageUrlPattern);
      });

      it('edit button click should load edit Apiperiodoassistencia page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Apiperiodoassistencia');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiperiodoassistenciaPageUrlPattern);
      });

      it('last delete button click should delete instance of Apiperiodoassistencia', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('apiperiodoassistencia').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apiperiodoassistenciaPageUrlPattern);

        apiperiodoassistencia = undefined;
      });
    });
  });

  describe('new Apiperiodoassistencia page', () => {
    beforeEach(() => {
      cy.visit(`${apiperiodoassistenciaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Apiperiodoassistencia');
    });

    it('should create an instance of Apiperiodoassistencia', () => {
      cy.get(`[data-cy="dadosperiodo"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="dadosperiodo"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="chave"]`).type('vibration what er');
      cy.get(`[data-cy="chave"]`).should('have.value', 'vibration what er');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        apiperiodoassistencia = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', apiperiodoassistenciaPageUrlPattern);
    });
  });
});
