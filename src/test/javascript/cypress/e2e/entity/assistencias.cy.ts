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

describe('Assistencias e2e test', () => {
  const assistenciasPageUrl = '/assistencias';
  const assistenciasPageUrlPattern = new RegExp('/assistencias(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const assistenciasSample = {};

  let assistencias;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/assistencias+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/assistencias').as('postEntityRequest');
    cy.intercept('DELETE', '/api/assistencias/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (assistencias) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/assistencias/${assistencias.id}`,
      }).then(() => {
        assistencias = undefined;
      });
    }
  });

  it('Assistencias menu should load Assistencias page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('assistencias');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Assistencias').should('exist');
    cy.url().should('match', assistenciasPageUrlPattern);
  });

  describe('Assistencias page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(assistenciasPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Assistencias page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/assistencias/new$'));
        cy.getEntityCreateUpdateHeading('Assistencias');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', assistenciasPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/assistencias',
          body: assistenciasSample,
        }).then(({ body }) => {
          assistencias = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/assistencias+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/assistencias?page=0&size=20>; rel="last",<http://localhost/api/assistencias?page=0&size=20>; rel="first"',
              },
              body: [assistencias],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(assistenciasPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Assistencias page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('assistencias');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', assistenciasPageUrlPattern);
      });

      it('edit button click should load edit Assistencias page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Assistencias');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', assistenciasPageUrlPattern);
      });

      it('edit button click should load edit Assistencias page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Assistencias');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', assistenciasPageUrlPattern);
      });

      it('last delete button click should delete instance of Assistencias', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('assistencias').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', assistenciasPageUrlPattern);

        assistencias = undefined;
      });
    });
  });

  describe('new Assistencias page', () => {
    beforeEach(() => {
      cy.visit(`${assistenciasPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Assistencias');
    });

    it('should create an instance of Assistencias', () => {
      cy.get(`[data-cy="value"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="value"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        assistencias = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', assistenciasPageUrlPattern);
    });
  });
});
