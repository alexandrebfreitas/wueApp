package com.alexandreburghesi.wattsupenergy.web.rest;

import static com.alexandreburghesi.wattsupenergy.domain.AutenticacaoAsserts.*;
import static com.alexandreburghesi.wattsupenergy.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.alexandreburghesi.wattsupenergy.IntegrationTest;
import com.alexandreburghesi.wattsupenergy.domain.Autenticacao;
import com.alexandreburghesi.wattsupenergy.repository.AutenticacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AutenticacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutenticacaoResourceIT {

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String DEFAULT_SENHA = "AAAAAAAAAA";
    private static final String UPDATED_SENHA = "BBBBBBBBBB";

    private static final String DEFAULT_ACCESS_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/autenticacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AutenticacaoRepository autenticacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutenticacaoMockMvc;

    private Autenticacao autenticacao;

    private Autenticacao insertedAutenticacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autenticacao createEntity() {
        return new Autenticacao()
            .usuario(DEFAULT_USUARIO)
            .senha(DEFAULT_SENHA)
            .accessToken(DEFAULT_ACCESS_TOKEN)
            .tokenType(DEFAULT_TOKEN_TYPE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autenticacao createUpdatedEntity() {
        return new Autenticacao()
            .usuario(UPDATED_USUARIO)
            .senha(UPDATED_SENHA)
            .accessToken(UPDATED_ACCESS_TOKEN)
            .tokenType(UPDATED_TOKEN_TYPE);
    }

    @BeforeEach
    public void initTest() {
        autenticacao = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAutenticacao != null) {
            autenticacaoRepository.delete(insertedAutenticacao);
            insertedAutenticacao = null;
        }
    }

    @Test
    @Transactional
    void createAutenticacao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Autenticacao
        var returnedAutenticacao = om.readValue(
            restAutenticacaoMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autenticacao))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Autenticacao.class
        );

        // Validate the Autenticacao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAutenticacaoUpdatableFieldsEquals(returnedAutenticacao, getPersistedAutenticacao(returnedAutenticacao));

        insertedAutenticacao = returnedAutenticacao;
    }

    @Test
    @Transactional
    void createAutenticacaoWithExistingId() throws Exception {
        // Create the Autenticacao with an existing ID
        autenticacao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutenticacaoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autenticacao)))
            .andExpect(status().isBadRequest());

        // Validate the Autenticacao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAutenticacaos() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList
        restAutenticacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autenticacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA)))
            .andExpect(jsonPath("$.[*].accessToken").value(hasItem(DEFAULT_ACCESS_TOKEN)))
            .andExpect(jsonPath("$.[*].tokenType").value(hasItem(DEFAULT_TOKEN_TYPE)));
    }

    @Test
    @Transactional
    void getAutenticacao() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get the autenticacao
        restAutenticacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, autenticacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autenticacao.getId().intValue()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO))
            .andExpect(jsonPath("$.senha").value(DEFAULT_SENHA))
            .andExpect(jsonPath("$.accessToken").value(DEFAULT_ACCESS_TOKEN))
            .andExpect(jsonPath("$.tokenType").value(DEFAULT_TOKEN_TYPE));
    }

    @Test
    @Transactional
    void getAutenticacaosByIdFiltering() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        Long id = autenticacao.getId();

        defaultAutenticacaoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAutenticacaoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAutenticacaoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAutenticacaosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where usuario equals to
        defaultAutenticacaoFiltering("usuario.equals=" + DEFAULT_USUARIO, "usuario.equals=" + UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void getAllAutenticacaosByUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where usuario in
        defaultAutenticacaoFiltering("usuario.in=" + DEFAULT_USUARIO + "," + UPDATED_USUARIO, "usuario.in=" + UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void getAllAutenticacaosByUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where usuario is not null
        defaultAutenticacaoFiltering("usuario.specified=true", "usuario.specified=false");
    }

    @Test
    @Transactional
    void getAllAutenticacaosByUsuarioContainsSomething() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where usuario contains
        defaultAutenticacaoFiltering("usuario.contains=" + DEFAULT_USUARIO, "usuario.contains=" + UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void getAllAutenticacaosByUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where usuario does not contain
        defaultAutenticacaoFiltering("usuario.doesNotContain=" + UPDATED_USUARIO, "usuario.doesNotContain=" + DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void getAllAutenticacaosBySenhaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where senha equals to
        defaultAutenticacaoFiltering("senha.equals=" + DEFAULT_SENHA, "senha.equals=" + UPDATED_SENHA);
    }

    @Test
    @Transactional
    void getAllAutenticacaosBySenhaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where senha in
        defaultAutenticacaoFiltering("senha.in=" + DEFAULT_SENHA + "," + UPDATED_SENHA, "senha.in=" + UPDATED_SENHA);
    }

    @Test
    @Transactional
    void getAllAutenticacaosBySenhaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where senha is not null
        defaultAutenticacaoFiltering("senha.specified=true", "senha.specified=false");
    }

    @Test
    @Transactional
    void getAllAutenticacaosBySenhaContainsSomething() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where senha contains
        defaultAutenticacaoFiltering("senha.contains=" + DEFAULT_SENHA, "senha.contains=" + UPDATED_SENHA);
    }

    @Test
    @Transactional
    void getAllAutenticacaosBySenhaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where senha does not contain
        defaultAutenticacaoFiltering("senha.doesNotContain=" + UPDATED_SENHA, "senha.doesNotContain=" + DEFAULT_SENHA);
    }

    @Test
    @Transactional
    void getAllAutenticacaosByAccessTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where accessToken equals to
        defaultAutenticacaoFiltering("accessToken.equals=" + DEFAULT_ACCESS_TOKEN, "accessToken.equals=" + UPDATED_ACCESS_TOKEN);
    }

    @Test
    @Transactional
    void getAllAutenticacaosByAccessTokenIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where accessToken in
        defaultAutenticacaoFiltering(
            "accessToken.in=" + DEFAULT_ACCESS_TOKEN + "," + UPDATED_ACCESS_TOKEN,
            "accessToken.in=" + UPDATED_ACCESS_TOKEN
        );
    }

    @Test
    @Transactional
    void getAllAutenticacaosByAccessTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where accessToken is not null
        defaultAutenticacaoFiltering("accessToken.specified=true", "accessToken.specified=false");
    }

    @Test
    @Transactional
    void getAllAutenticacaosByAccessTokenContainsSomething() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where accessToken contains
        defaultAutenticacaoFiltering("accessToken.contains=" + DEFAULT_ACCESS_TOKEN, "accessToken.contains=" + UPDATED_ACCESS_TOKEN);
    }

    @Test
    @Transactional
    void getAllAutenticacaosByAccessTokenNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where accessToken does not contain
        defaultAutenticacaoFiltering(
            "accessToken.doesNotContain=" + UPDATED_ACCESS_TOKEN,
            "accessToken.doesNotContain=" + DEFAULT_ACCESS_TOKEN
        );
    }

    @Test
    @Transactional
    void getAllAutenticacaosByTokenTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where tokenType equals to
        defaultAutenticacaoFiltering("tokenType.equals=" + DEFAULT_TOKEN_TYPE, "tokenType.equals=" + UPDATED_TOKEN_TYPE);
    }

    @Test
    @Transactional
    void getAllAutenticacaosByTokenTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where tokenType in
        defaultAutenticacaoFiltering("tokenType.in=" + DEFAULT_TOKEN_TYPE + "," + UPDATED_TOKEN_TYPE, "tokenType.in=" + UPDATED_TOKEN_TYPE);
    }

    @Test
    @Transactional
    void getAllAutenticacaosByTokenTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where tokenType is not null
        defaultAutenticacaoFiltering("tokenType.specified=true", "tokenType.specified=false");
    }

    @Test
    @Transactional
    void getAllAutenticacaosByTokenTypeContainsSomething() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where tokenType contains
        defaultAutenticacaoFiltering("tokenType.contains=" + DEFAULT_TOKEN_TYPE, "tokenType.contains=" + UPDATED_TOKEN_TYPE);
    }

    @Test
    @Transactional
    void getAllAutenticacaosByTokenTypeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        // Get all the autenticacaoList where tokenType does not contain
        defaultAutenticacaoFiltering("tokenType.doesNotContain=" + UPDATED_TOKEN_TYPE, "tokenType.doesNotContain=" + DEFAULT_TOKEN_TYPE);
    }

    private void defaultAutenticacaoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAutenticacaoShouldBeFound(shouldBeFound);
        defaultAutenticacaoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAutenticacaoShouldBeFound(String filter) throws Exception {
        restAutenticacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autenticacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA)))
            .andExpect(jsonPath("$.[*].accessToken").value(hasItem(DEFAULT_ACCESS_TOKEN)))
            .andExpect(jsonPath("$.[*].tokenType").value(hasItem(DEFAULT_TOKEN_TYPE)));

        // Check, that the count call also returns 1
        restAutenticacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAutenticacaoShouldNotBeFound(String filter) throws Exception {
        restAutenticacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAutenticacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAutenticacao() throws Exception {
        // Get the autenticacao
        restAutenticacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutenticacao() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autenticacao
        Autenticacao updatedAutenticacao = autenticacaoRepository.findById(autenticacao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAutenticacao are not directly saved in db
        em.detach(updatedAutenticacao);
        updatedAutenticacao.usuario(UPDATED_USUARIO).senha(UPDATED_SENHA).accessToken(UPDATED_ACCESS_TOKEN).tokenType(UPDATED_TOKEN_TYPE);

        restAutenticacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutenticacao.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAutenticacao))
            )
            .andExpect(status().isOk());

        // Validate the Autenticacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAutenticacaoToMatchAllProperties(updatedAutenticacao);
    }

    @Test
    @Transactional
    void putNonExistingAutenticacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autenticacao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutenticacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autenticacao.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autenticacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autenticacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutenticacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autenticacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutenticacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autenticacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autenticacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutenticacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autenticacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutenticacaoMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autenticacao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autenticacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutenticacaoWithPatch() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autenticacao using partial update
        Autenticacao partialUpdatedAutenticacao = new Autenticacao();
        partialUpdatedAutenticacao.setId(autenticacao.getId());

        restAutenticacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutenticacao.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutenticacao))
            )
            .andExpect(status().isOk());

        // Validate the Autenticacao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutenticacaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAutenticacao, autenticacao),
            getPersistedAutenticacao(autenticacao)
        );
    }

    @Test
    @Transactional
    void fullUpdateAutenticacaoWithPatch() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autenticacao using partial update
        Autenticacao partialUpdatedAutenticacao = new Autenticacao();
        partialUpdatedAutenticacao.setId(autenticacao.getId());

        partialUpdatedAutenticacao
            .usuario(UPDATED_USUARIO)
            .senha(UPDATED_SENHA)
            .accessToken(UPDATED_ACCESS_TOKEN)
            .tokenType(UPDATED_TOKEN_TYPE);

        restAutenticacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutenticacao.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutenticacao))
            )
            .andExpect(status().isOk());

        // Validate the Autenticacao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutenticacaoUpdatableFieldsEquals(partialUpdatedAutenticacao, getPersistedAutenticacao(partialUpdatedAutenticacao));
    }

    @Test
    @Transactional
    void patchNonExistingAutenticacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autenticacao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutenticacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autenticacao.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autenticacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autenticacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutenticacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autenticacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutenticacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autenticacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autenticacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutenticacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autenticacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutenticacaoMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(autenticacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autenticacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutenticacao() throws Exception {
        // Initialize the database
        insertedAutenticacao = autenticacaoRepository.saveAndFlush(autenticacao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the autenticacao
        restAutenticacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, autenticacao.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return autenticacaoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Autenticacao getPersistedAutenticacao(Autenticacao autenticacao) {
        return autenticacaoRepository.findById(autenticacao.getId()).orElseThrow();
    }

    protected void assertPersistedAutenticacaoToMatchAllProperties(Autenticacao expectedAutenticacao) {
        assertAutenticacaoAllPropertiesEquals(expectedAutenticacao, getPersistedAutenticacao(expectedAutenticacao));
    }

    protected void assertPersistedAutenticacaoToMatchUpdatableProperties(Autenticacao expectedAutenticacao) {
        assertAutenticacaoAllUpdatablePropertiesEquals(expectedAutenticacao, getPersistedAutenticacao(expectedAutenticacao));
    }
}
