package com.alexandreburghesi.wattsupenergy.web.rest;

import static com.alexandreburghesi.wattsupenergy.domain.ApiindicadorAsserts.*;
import static com.alexandreburghesi.wattsupenergy.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.alexandreburghesi.wattsupenergy.IntegrationTest;
import com.alexandreburghesi.wattsupenergy.domain.Apiindicador;
import com.alexandreburghesi.wattsupenergy.repository.ApiindicadorRepository;
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
 * Integration tests for the {@link ApiindicadorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApiindicadorResourceIT {

    private static final String DEFAULT_FILTRO = "AAAAAAAAAA";
    private static final String UPDATED_FILTRO = "BBBBBBBBBB";

    private static final String DEFAULT_DADOSINDICADOR = "AAAAAAAAAA";
    private static final String UPDATED_DADOSINDICADOR = "BBBBBBBBBB";

    private static final String DEFAULT_CHAVE = "AAAAAAAAAA";
    private static final String UPDATED_CHAVE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/apiindicadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ApiindicadorRepository apiindicadorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApiindicadorMockMvc;

    private Apiindicador apiindicador;

    private Apiindicador insertedApiindicador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apiindicador createEntity() {
        return new Apiindicador().filtro(DEFAULT_FILTRO).dadosindicador(DEFAULT_DADOSINDICADOR).chave(DEFAULT_CHAVE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apiindicador createUpdatedEntity() {
        return new Apiindicador().filtro(UPDATED_FILTRO).dadosindicador(UPDATED_DADOSINDICADOR).chave(UPDATED_CHAVE);
    }

    @BeforeEach
    public void initTest() {
        apiindicador = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedApiindicador != null) {
            apiindicadorRepository.delete(insertedApiindicador);
            insertedApiindicador = null;
        }
    }

    @Test
    @Transactional
    void createApiindicador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Apiindicador
        var returnedApiindicador = om.readValue(
            restApiindicadorMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiindicador))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Apiindicador.class
        );

        // Validate the Apiindicador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertApiindicadorUpdatableFieldsEquals(returnedApiindicador, getPersistedApiindicador(returnedApiindicador));

        insertedApiindicador = returnedApiindicador;
    }

    @Test
    @Transactional
    void createApiindicadorWithExistingId() throws Exception {
        // Create the Apiindicador with an existing ID
        apiindicador.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiindicadorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiindicador)))
            .andExpect(status().isBadRequest());

        // Validate the Apiindicador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApiindicadors() throws Exception {
        // Initialize the database
        insertedApiindicador = apiindicadorRepository.saveAndFlush(apiindicador);

        // Get all the apiindicadorList
        restApiindicadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiindicador.getId().intValue())))
            .andExpect(jsonPath("$.[*].filtro").value(hasItem(DEFAULT_FILTRO)))
            .andExpect(jsonPath("$.[*].dadosindicador").value(hasItem(DEFAULT_DADOSINDICADOR)))
            .andExpect(jsonPath("$.[*].chave").value(hasItem(DEFAULT_CHAVE)));
    }

    @Test
    @Transactional
    void getApiindicador() throws Exception {
        // Initialize the database
        insertedApiindicador = apiindicadorRepository.saveAndFlush(apiindicador);

        // Get the apiindicador
        restApiindicadorMockMvc
            .perform(get(ENTITY_API_URL_ID, apiindicador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apiindicador.getId().intValue()))
            .andExpect(jsonPath("$.filtro").value(DEFAULT_FILTRO))
            .andExpect(jsonPath("$.dadosindicador").value(DEFAULT_DADOSINDICADOR))
            .andExpect(jsonPath("$.chave").value(DEFAULT_CHAVE));
    }

    @Test
    @Transactional
    void getApiindicadorsByIdFiltering() throws Exception {
        // Initialize the database
        insertedApiindicador = apiindicadorRepository.saveAndFlush(apiindicador);

        Long id = apiindicador.getId();

        defaultApiindicadorFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultApiindicadorFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultApiindicadorFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApiindicadorsByChaveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedApiindicador = apiindicadorRepository.saveAndFlush(apiindicador);

        // Get all the apiindicadorList where chave equals to
        defaultApiindicadorFiltering("chave.equals=" + DEFAULT_CHAVE, "chave.equals=" + UPDATED_CHAVE);
    }

    @Test
    @Transactional
    void getAllApiindicadorsByChaveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedApiindicador = apiindicadorRepository.saveAndFlush(apiindicador);

        // Get all the apiindicadorList where chave in
        defaultApiindicadorFiltering("chave.in=" + DEFAULT_CHAVE + "," + UPDATED_CHAVE, "chave.in=" + UPDATED_CHAVE);
    }

    @Test
    @Transactional
    void getAllApiindicadorsByChaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedApiindicador = apiindicadorRepository.saveAndFlush(apiindicador);

        // Get all the apiindicadorList where chave is not null
        defaultApiindicadorFiltering("chave.specified=true", "chave.specified=false");
    }

    @Test
    @Transactional
    void getAllApiindicadorsByChaveContainsSomething() throws Exception {
        // Initialize the database
        insertedApiindicador = apiindicadorRepository.saveAndFlush(apiindicador);

        // Get all the apiindicadorList where chave contains
        defaultApiindicadorFiltering("chave.contains=" + DEFAULT_CHAVE, "chave.contains=" + UPDATED_CHAVE);
    }

    @Test
    @Transactional
    void getAllApiindicadorsByChaveNotContainsSomething() throws Exception {
        // Initialize the database
        insertedApiindicador = apiindicadorRepository.saveAndFlush(apiindicador);

        // Get all the apiindicadorList where chave does not contain
        defaultApiindicadorFiltering("chave.doesNotContain=" + UPDATED_CHAVE, "chave.doesNotContain=" + DEFAULT_CHAVE);
    }

    private void defaultApiindicadorFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultApiindicadorShouldBeFound(shouldBeFound);
        defaultApiindicadorShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApiindicadorShouldBeFound(String filter) throws Exception {
        restApiindicadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiindicador.getId().intValue())))
            .andExpect(jsonPath("$.[*].filtro").value(hasItem(DEFAULT_FILTRO)))
            .andExpect(jsonPath("$.[*].dadosindicador").value(hasItem(DEFAULT_DADOSINDICADOR)))
            .andExpect(jsonPath("$.[*].chave").value(hasItem(DEFAULT_CHAVE)));

        // Check, that the count call also returns 1
        restApiindicadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApiindicadorShouldNotBeFound(String filter) throws Exception {
        restApiindicadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApiindicadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApiindicador() throws Exception {
        // Get the apiindicador
        restApiindicadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApiindicador() throws Exception {
        // Initialize the database
        insertedApiindicador = apiindicadorRepository.saveAndFlush(apiindicador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiindicador
        Apiindicador updatedApiindicador = apiindicadorRepository.findById(apiindicador.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedApiindicador are not directly saved in db
        em.detach(updatedApiindicador);
        updatedApiindicador.filtro(UPDATED_FILTRO).dadosindicador(UPDATED_DADOSINDICADOR).chave(UPDATED_CHAVE);

        restApiindicadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApiindicador.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedApiindicador))
            )
            .andExpect(status().isOk());

        // Validate the Apiindicador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedApiindicadorToMatchAllProperties(updatedApiindicador);
    }

    @Test
    @Transactional
    void putNonExistingApiindicador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiindicador.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiindicadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiindicador.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apiindicador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apiindicador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApiindicador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiindicador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiindicadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apiindicador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apiindicador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApiindicador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiindicador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiindicadorMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiindicador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apiindicador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApiindicadorWithPatch() throws Exception {
        // Initialize the database
        insertedApiindicador = apiindicadorRepository.saveAndFlush(apiindicador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiindicador using partial update
        Apiindicador partialUpdatedApiindicador = new Apiindicador();
        partialUpdatedApiindicador.setId(apiindicador.getId());

        partialUpdatedApiindicador.dadosindicador(UPDATED_DADOSINDICADOR);

        restApiindicadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiindicador.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApiindicador))
            )
            .andExpect(status().isOk());

        // Validate the Apiindicador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApiindicadorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedApiindicador, apiindicador),
            getPersistedApiindicador(apiindicador)
        );
    }

    @Test
    @Transactional
    void fullUpdateApiindicadorWithPatch() throws Exception {
        // Initialize the database
        insertedApiindicador = apiindicadorRepository.saveAndFlush(apiindicador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiindicador using partial update
        Apiindicador partialUpdatedApiindicador = new Apiindicador();
        partialUpdatedApiindicador.setId(apiindicador.getId());

        partialUpdatedApiindicador.filtro(UPDATED_FILTRO).dadosindicador(UPDATED_DADOSINDICADOR).chave(UPDATED_CHAVE);

        restApiindicadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiindicador.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApiindicador))
            )
            .andExpect(status().isOk());

        // Validate the Apiindicador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApiindicadorUpdatableFieldsEquals(partialUpdatedApiindicador, getPersistedApiindicador(partialUpdatedApiindicador));
    }

    @Test
    @Transactional
    void patchNonExistingApiindicador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiindicador.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiindicadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apiindicador.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apiindicador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apiindicador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApiindicador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiindicador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiindicadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apiindicador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apiindicador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApiindicador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiindicador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiindicadorMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(apiindicador))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apiindicador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApiindicador() throws Exception {
        // Initialize the database
        insertedApiindicador = apiindicadorRepository.saveAndFlush(apiindicador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the apiindicador
        restApiindicadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, apiindicador.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return apiindicadorRepository.count();
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

    protected Apiindicador getPersistedApiindicador(Apiindicador apiindicador) {
        return apiindicadorRepository.findById(apiindicador.getId()).orElseThrow();
    }

    protected void assertPersistedApiindicadorToMatchAllProperties(Apiindicador expectedApiindicador) {
        assertApiindicadorAllPropertiesEquals(expectedApiindicador, getPersistedApiindicador(expectedApiindicador));
    }

    protected void assertPersistedApiindicadorToMatchUpdatableProperties(Apiindicador expectedApiindicador) {
        assertApiindicadorAllUpdatablePropertiesEquals(expectedApiindicador, getPersistedApiindicador(expectedApiindicador));
    }
}
