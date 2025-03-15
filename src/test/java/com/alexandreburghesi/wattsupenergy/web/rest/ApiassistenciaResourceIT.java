package com.alexandreburghesi.wattsupenergy.web.rest;

import static com.alexandreburghesi.wattsupenergy.domain.ApiassistenciaAsserts.*;
import static com.alexandreburghesi.wattsupenergy.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.alexandreburghesi.wattsupenergy.IntegrationTest;
import com.alexandreburghesi.wattsupenergy.domain.Apiassistencia;
import com.alexandreburghesi.wattsupenergy.repository.ApiassistenciaRepository;
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
 * Integration tests for the {@link ApiassistenciaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApiassistenciaResourceIT {

    private static final String DEFAULT_FILTRO = "AAAAAAAAAA";
    private static final String UPDATED_FILTRO = "BBBBBBBBBB";

    private static final String DEFAULT_DADOSASSISTENCIA = "AAAAAAAAAA";
    private static final String UPDATED_DADOSASSISTENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_CHAVE = "AAAAAAAAAA";
    private static final String UPDATED_CHAVE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/apiassistencias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ApiassistenciaRepository apiassistenciaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApiassistenciaMockMvc;

    private Apiassistencia apiassistencia;

    private Apiassistencia insertedApiassistencia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apiassistencia createEntity() {
        return new Apiassistencia().filtro(DEFAULT_FILTRO).dadosassistencia(DEFAULT_DADOSASSISTENCIA).chave(DEFAULT_CHAVE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apiassistencia createUpdatedEntity() {
        return new Apiassistencia().filtro(UPDATED_FILTRO).dadosassistencia(UPDATED_DADOSASSISTENCIA).chave(UPDATED_CHAVE);
    }

    @BeforeEach
    public void initTest() {
        apiassistencia = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedApiassistencia != null) {
            apiassistenciaRepository.delete(insertedApiassistencia);
            insertedApiassistencia = null;
        }
    }

    @Test
    @Transactional
    void createApiassistencia() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Apiassistencia
        var returnedApiassistencia = om.readValue(
            restApiassistenciaMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiassistencia))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Apiassistencia.class
        );

        // Validate the Apiassistencia in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertApiassistenciaUpdatableFieldsEquals(returnedApiassistencia, getPersistedApiassistencia(returnedApiassistencia));

        insertedApiassistencia = returnedApiassistencia;
    }

    @Test
    @Transactional
    void createApiassistenciaWithExistingId() throws Exception {
        // Create the Apiassistencia with an existing ID
        apiassistencia.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiassistenciaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiassistencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apiassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApiassistencias() throws Exception {
        // Initialize the database
        insertedApiassistencia = apiassistenciaRepository.saveAndFlush(apiassistencia);

        // Get all the apiassistenciaList
        restApiassistenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiassistencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].filtro").value(hasItem(DEFAULT_FILTRO)))
            .andExpect(jsonPath("$.[*].dadosassistencia").value(hasItem(DEFAULT_DADOSASSISTENCIA)))
            .andExpect(jsonPath("$.[*].chave").value(hasItem(DEFAULT_CHAVE)));
    }

    @Test
    @Transactional
    void getApiassistencia() throws Exception {
        // Initialize the database
        insertedApiassistencia = apiassistenciaRepository.saveAndFlush(apiassistencia);

        // Get the apiassistencia
        restApiassistenciaMockMvc
            .perform(get(ENTITY_API_URL_ID, apiassistencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apiassistencia.getId().intValue()))
            .andExpect(jsonPath("$.filtro").value(DEFAULT_FILTRO))
            .andExpect(jsonPath("$.dadosassistencia").value(DEFAULT_DADOSASSISTENCIA))
            .andExpect(jsonPath("$.chave").value(DEFAULT_CHAVE));
    }

    @Test
    @Transactional
    void getApiassistenciasByIdFiltering() throws Exception {
        // Initialize the database
        insertedApiassistencia = apiassistenciaRepository.saveAndFlush(apiassistencia);

        Long id = apiassistencia.getId();

        defaultApiassistenciaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultApiassistenciaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultApiassistenciaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApiassistenciasByChaveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedApiassistencia = apiassistenciaRepository.saveAndFlush(apiassistencia);

        // Get all the apiassistenciaList where chave equals to
        defaultApiassistenciaFiltering("chave.equals=" + DEFAULT_CHAVE, "chave.equals=" + UPDATED_CHAVE);
    }

    @Test
    @Transactional
    void getAllApiassistenciasByChaveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedApiassistencia = apiassistenciaRepository.saveAndFlush(apiassistencia);

        // Get all the apiassistenciaList where chave in
        defaultApiassistenciaFiltering("chave.in=" + DEFAULT_CHAVE + "," + UPDATED_CHAVE, "chave.in=" + UPDATED_CHAVE);
    }

    @Test
    @Transactional
    void getAllApiassistenciasByChaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedApiassistencia = apiassistenciaRepository.saveAndFlush(apiassistencia);

        // Get all the apiassistenciaList where chave is not null
        defaultApiassistenciaFiltering("chave.specified=true", "chave.specified=false");
    }

    @Test
    @Transactional
    void getAllApiassistenciasByChaveContainsSomething() throws Exception {
        // Initialize the database
        insertedApiassistencia = apiassistenciaRepository.saveAndFlush(apiassistencia);

        // Get all the apiassistenciaList where chave contains
        defaultApiassistenciaFiltering("chave.contains=" + DEFAULT_CHAVE, "chave.contains=" + UPDATED_CHAVE);
    }

    @Test
    @Transactional
    void getAllApiassistenciasByChaveNotContainsSomething() throws Exception {
        // Initialize the database
        insertedApiassistencia = apiassistenciaRepository.saveAndFlush(apiassistencia);

        // Get all the apiassistenciaList where chave does not contain
        defaultApiassistenciaFiltering("chave.doesNotContain=" + UPDATED_CHAVE, "chave.doesNotContain=" + DEFAULT_CHAVE);
    }

    private void defaultApiassistenciaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultApiassistenciaShouldBeFound(shouldBeFound);
        defaultApiassistenciaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApiassistenciaShouldBeFound(String filter) throws Exception {
        restApiassistenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiassistencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].filtro").value(hasItem(DEFAULT_FILTRO)))
            .andExpect(jsonPath("$.[*].dadosassistencia").value(hasItem(DEFAULT_DADOSASSISTENCIA)))
            .andExpect(jsonPath("$.[*].chave").value(hasItem(DEFAULT_CHAVE)));

        // Check, that the count call also returns 1
        restApiassistenciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApiassistenciaShouldNotBeFound(String filter) throws Exception {
        restApiassistenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApiassistenciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApiassistencia() throws Exception {
        // Get the apiassistencia
        restApiassistenciaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApiassistencia() throws Exception {
        // Initialize the database
        insertedApiassistencia = apiassistenciaRepository.saveAndFlush(apiassistencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiassistencia
        Apiassistencia updatedApiassistencia = apiassistenciaRepository.findById(apiassistencia.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedApiassistencia are not directly saved in db
        em.detach(updatedApiassistencia);
        updatedApiassistencia.filtro(UPDATED_FILTRO).dadosassistencia(UPDATED_DADOSASSISTENCIA).chave(UPDATED_CHAVE);

        restApiassistenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApiassistencia.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedApiassistencia))
            )
            .andExpect(status().isOk());

        // Validate the Apiassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedApiassistenciaToMatchAllProperties(updatedApiassistencia);
    }

    @Test
    @Transactional
    void putNonExistingApiassistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiassistencia.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiassistenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiassistencia.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apiassistencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apiassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApiassistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiassistencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiassistenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apiassistencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apiassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApiassistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiassistencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiassistenciaMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiassistencia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apiassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApiassistenciaWithPatch() throws Exception {
        // Initialize the database
        insertedApiassistencia = apiassistenciaRepository.saveAndFlush(apiassistencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiassistencia using partial update
        Apiassistencia partialUpdatedApiassistencia = new Apiassistencia();
        partialUpdatedApiassistencia.setId(apiassistencia.getId());

        partialUpdatedApiassistencia.filtro(UPDATED_FILTRO).dadosassistencia(UPDATED_DADOSASSISTENCIA);

        restApiassistenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiassistencia.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApiassistencia))
            )
            .andExpect(status().isOk());

        // Validate the Apiassistencia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApiassistenciaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedApiassistencia, apiassistencia),
            getPersistedApiassistencia(apiassistencia)
        );
    }

    @Test
    @Transactional
    void fullUpdateApiassistenciaWithPatch() throws Exception {
        // Initialize the database
        insertedApiassistencia = apiassistenciaRepository.saveAndFlush(apiassistencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiassistencia using partial update
        Apiassistencia partialUpdatedApiassistencia = new Apiassistencia();
        partialUpdatedApiassistencia.setId(apiassistencia.getId());

        partialUpdatedApiassistencia.filtro(UPDATED_FILTRO).dadosassistencia(UPDATED_DADOSASSISTENCIA).chave(UPDATED_CHAVE);

        restApiassistenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiassistencia.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApiassistencia))
            )
            .andExpect(status().isOk());

        // Validate the Apiassistencia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApiassistenciaUpdatableFieldsEquals(partialUpdatedApiassistencia, getPersistedApiassistencia(partialUpdatedApiassistencia));
    }

    @Test
    @Transactional
    void patchNonExistingApiassistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiassistencia.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiassistenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apiassistencia.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apiassistencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apiassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApiassistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiassistencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiassistenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apiassistencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apiassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApiassistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiassistencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiassistenciaMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(apiassistencia))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apiassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApiassistencia() throws Exception {
        // Initialize the database
        insertedApiassistencia = apiassistenciaRepository.saveAndFlush(apiassistencia);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the apiassistencia
        restApiassistenciaMockMvc
            .perform(delete(ENTITY_API_URL_ID, apiassistencia.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return apiassistenciaRepository.count();
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

    protected Apiassistencia getPersistedApiassistencia(Apiassistencia apiassistencia) {
        return apiassistenciaRepository.findById(apiassistencia.getId()).orElseThrow();
    }

    protected void assertPersistedApiassistenciaToMatchAllProperties(Apiassistencia expectedApiassistencia) {
        assertApiassistenciaAllPropertiesEquals(expectedApiassistencia, getPersistedApiassistencia(expectedApiassistencia));
    }

    protected void assertPersistedApiassistenciaToMatchUpdatableProperties(Apiassistencia expectedApiassistencia) {
        assertApiassistenciaAllUpdatablePropertiesEquals(expectedApiassistencia, getPersistedApiassistencia(expectedApiassistencia));
    }
}
