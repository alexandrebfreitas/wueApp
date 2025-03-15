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

describe('Autenticacao e2e test', () => {
  const autenticacaoPageUrl = '/autenticacao';
  const autenticacaoPageUrlPattern = new RegExp('/autenticacao(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const autenticacaoSample = {};

  let autenticacao;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/autenticacaos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/autenticacaos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/autenticacaos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (autenticacao) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/autenticacaos/${autenticacao.id}`,
      }).then(() => {
        autenticacao = undefined;
      });
    }
  });

  it('Autenticacaos menu should load Autenticacaos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('autenticacao');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Autenticacao').should('exist');
    cy.url().should('match', autenticacaoPageUrlPattern);
  });

  describe('Autenticacao page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(autenticacaoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Autenticacao page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/autenticacao/new$'));
        cy.getEntityCreateUpdateHeading('Autenticacao');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', autenticacaoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/autenticacaos',
          body: autenticacaoSample,
        }).then(({ body }) => {
          autenticacao = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/autenticacaos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/autenticacaos?page=0&size=20>; rel="last",<http://localhost/api/autenticacaos?page=0&size=20>; rel="first"',
              },
              body: [autenticacao],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(autenticacaoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Autenticacao page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('autenticacao');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', autenticacaoPageUrlPattern);
      });

      it('edit button click should load edit Autenticacao page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Autenticacao');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', autenticacaoPageUrlPattern);
      });

      it('edit button click should load edit Autenticacao page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Autenticacao');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', autenticacaoPageUrlPattern);
      });

      it('last delete button click should delete instance of Autenticacao', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('autenticacao').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', autenticacaoPageUrlPattern);

        autenticacao = undefined;
      });
    });
  });

  describe('new Autenticacao page', () => {
    beforeEach(() => {
      cy.visit(`${autenticacaoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Autenticacao');
    });

    it('should create an instance of Autenticacao', () => {
      cy.get(`[data-cy="usuario"]`).type('pfft deceivingly');
      cy.get(`[data-cy="usuario"]`).should('have.value', 'pfft deceivingly');

      cy.get(`[data-cy="senha"]`).type('palatable');
      cy.get(`[data-cy="senha"]`).should('have.value', 'palatable');

      cy.get(`[data-cy="accessToken"]`).type('scientific presume amongst');
      cy.get(`[data-cy="accessToken"]`).should('have.value', 'scientific presume amongst');

      cy.get(`[data-cy="tokenType"]`).type('impressionable');
      cy.get(`[data-cy="tokenType"]`).should('have.value', 'impressionable');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        autenticacao = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', autenticacaoPageUrlPattern);
    });
  });
});
