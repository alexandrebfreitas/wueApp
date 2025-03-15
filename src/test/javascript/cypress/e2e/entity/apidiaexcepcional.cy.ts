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

describe('Apidiaexcepcional e2e test', () => {
  const apidiaexcepcionalPageUrl = '/apidiaexcepcional';
  const apidiaexcepcionalPageUrlPattern = new RegExp('/apidiaexcepcional(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const apidiaexcepcionalSample = {};

  let apidiaexcepcional;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/apidiaexcepcionals+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/apidiaexcepcionals').as('postEntityRequest');
    cy.intercept('DELETE', '/api/apidiaexcepcionals/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (apidiaexcepcional) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/apidiaexcepcionals/${apidiaexcepcional.id}`,
      }).then(() => {
        apidiaexcepcional = undefined;
      });
    }
  });

  it('Apidiaexcepcionals menu should load Apidiaexcepcionals page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('apidiaexcepcional');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Apidiaexcepcional').should('exist');
    cy.url().should('match', apidiaexcepcionalPageUrlPattern);
  });

  describe('Apidiaexcepcional page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(apidiaexcepcionalPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Apidiaexcepcional page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/apidiaexcepcional/new$'));
        cy.getEntityCreateUpdateHeading('Apidiaexcepcional');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apidiaexcepcionalPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/apidiaexcepcionals',
          body: apidiaexcepcionalSample,
        }).then(({ body }) => {
          apidiaexcepcional = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/apidiaexcepcionals+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/apidiaexcepcionals?page=0&size=20>; rel="last",<http://localhost/api/apidiaexcepcionals?page=0&size=20>; rel="first"',
              },
              body: [apidiaexcepcional],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(apidiaexcepcionalPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Apidiaexcepcional page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('apidiaexcepcional');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apidiaexcepcionalPageUrlPattern);
      });

      it('edit button click should load edit Apidiaexcepcional page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Apidiaexcepcional');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apidiaexcepcionalPageUrlPattern);
      });

      it('edit button click should load edit Apidiaexcepcional page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Apidiaexcepcional');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apidiaexcepcionalPageUrlPattern);
      });

      it('last delete button click should delete instance of Apidiaexcepcional', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('apidiaexcepcional').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', apidiaexcepcionalPageUrlPattern);

        apidiaexcepcional = undefined;
      });
    });
  });

  describe('new Apidiaexcepcional page', () => {
    beforeEach(() => {
      cy.visit(`${apidiaexcepcionalPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Apidiaexcepcional');
    });

    it('should create an instance of Apidiaexcepcional', () => {
      cy.get(`[data-cy="dadosdiaexcepcional"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="dadosdiaexcepcional"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="chave"]`).type('firsthand');
      cy.get(`[data-cy="chave"]`).should('have.value', 'firsthand');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        apidiaexcepcional = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', apidiaexcepcionalPageUrlPattern);
    });
  });
});
