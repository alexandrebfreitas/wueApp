package com.alexandreburghesi.wattsupenergy.web.rest;

import static com.alexandreburghesi.wattsupenergy.domain.ApidiaexcepcionalAsserts.*;
import static com.alexandreburghesi.wattsupenergy.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.alexandreburghesi.wattsupenergy.IntegrationTest;
import com.alexandreburghesi.wattsupenergy.domain.Apidiaexcepcional;
import com.alexandreburghesi.wattsupenergy.repository.ApidiaexcepcionalRepository;
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
 * Integration tests for the {@link ApidiaexcepcionalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApidiaexcepcionalResourceIT {

    private static final String DEFAULT_DADOSDIAEXCEPCIONAL = "AAAAAAAAAA";
    private static final String UPDATED_DADOSDIAEXCEPCIONAL = "BBBBBBBBBB";

    private static final String DEFAULT_CHAVE = "AAAAAAAAAA";
    private static final String UPDATED_CHAVE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/apidiaexcepcionals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ApidiaexcepcionalRepository apidiaexcepcionalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApidiaexcepcionalMockMvc;

    private Apidiaexcepcional apidiaexcepcional;

    private Apidiaexcepcional insertedApidiaexcepcional;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apidiaexcepcional createEntity() {
        return new Apidiaexcepcional().dadosdiaexcepcional(DEFAULT_DADOSDIAEXCEPCIONAL).chave(DEFAULT_CHAVE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apidiaexcepcional createUpdatedEntity() {
        return new Apidiaexcepcional().dadosdiaexcepcional(UPDATED_DADOSDIAEXCEPCIONAL).chave(UPDATED_CHAVE);
    }

    @BeforeEach
    public void initTest() {
        apidiaexcepcional = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedApidiaexcepcional != null) {
            apidiaexcepcionalRepository.delete(insertedApidiaexcepcional);
            insertedApidiaexcepcional = null;
        }
    }

    @Test
    @Transactional
    void createApidiaexcepcional() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Apidiaexcepcional
        var returnedApidiaexcepcional = om.readValue(
            restApidiaexcepcionalMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(apidiaexcepcional))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Apidiaexcepcional.class
        );

        // Validate the Apidiaexcepcional in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertApidiaexcepcionalUpdatableFieldsEquals(returnedApidiaexcepcional, getPersistedApidiaexcepcional(returnedApidiaexcepcional));

        insertedApidiaexcepcional = returnedApidiaexcepcional;
    }

    @Test
    @Transactional
    void createApidiaexcepcionalWithExistingId() throws Exception {
        // Create the Apidiaexcepcional with an existing ID
        apidiaexcepcional.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApidiaexcepcionalMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apidiaexcepcional))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apidiaexcepcional in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApidiaexcepcionals() throws Exception {
        // Initialize the database
        insertedApidiaexcepcional = apidiaexcepcionalRepository.saveAndFlush(apidiaexcepcional);

        // Get all the apidiaexcepcionalList
        restApidiaexcepcionalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apidiaexcepcional.getId().intValue())))
            .andExpect(jsonPath("$.[*].dadosdiaexcepcional").value(hasItem(DEFAULT_DADOSDIAEXCEPCIONAL)))
            .andExpect(jsonPath("$.[*].chave").value(hasItem(DEFAULT_CHAVE)));
    }

    @Test
    @Transactional
    void getApidiaexcepcional() throws Exception {
        // Initialize the database
        insertedApidiaexcepcional = apidiaexcepcionalRepository.saveAndFlush(apidiaexcepcional);

        // Get the apidiaexcepcional
        restApidiaexcepcionalMockMvc
            .perform(get(ENTITY_API_URL_ID, apidiaexcepcional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apidiaexcepcional.getId().intValue()))
            .andExpect(jsonPath("$.dadosdiaexcepcional").value(DEFAULT_DADOSDIAEXCEPCIONAL))
            .andExpect(jsonPath("$.chave").value(DEFAULT_CHAVE));
    }

    @Test
    @Transactional
    void getApidiaexcepcionalsByIdFiltering() throws Exception {
        // Initialize the database
        insertedApidiaexcepcional = apidiaexcepcionalRepository.saveAndFlush(apidiaexcepcional);

        Long id = apidiaexcepcional.getId();

        defaultApidiaexcepcionalFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultApidiaexcepcionalFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultApidiaexcepcionalFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApidiaexcepcionalsByChaveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedApidiaexcepcional = apidiaexcepcionalRepository.saveAndFlush(apidiaexcepcional);

        // Get all the apidiaexcepcionalList where chave equals to
        defaultApidiaexcepcionalFiltering("chave.equals=" + DEFAULT_CHAVE, "chave.equals=" + UPDATED_CHAVE);
    }

    @Test
    @Transactional
    void getAllApidiaexcepcionalsByChaveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedApidiaexcepcional = apidiaexcepcionalRepository.saveAndFlush(apidiaexcepcional);

        // Get all the apidiaexcepcionalList where chave in
        defaultApidiaexcepcionalFiltering("chave.in=" + DEFAULT_CHAVE + "," + UPDATED_CHAVE, "chave.in=" + UPDATED_CHAVE);
    }

    @Test
    @Transactional
    void getAllApidiaexcepcionalsByChaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedApidiaexcepcional = apidiaexcepcionalRepository.saveAndFlush(apidiaexcepcional);

        // Get all the apidiaexcepcionalList where chave is not null
        defaultApidiaexcepcionalFiltering("chave.specified=true", "chave.specified=false");
    }

    @Test
    @Transactional
    void getAllApidiaexcepcionalsByChaveContainsSomething() throws Exception {
        // Initialize the database
        insertedApidiaexcepcional = apidiaexcepcionalRepository.saveAndFlush(apidiaexcepcional);

        // Get all the apidiaexcepcionalList where chave contains
        defaultApidiaexcepcionalFiltering("chave.contains=" + DEFAULT_CHAVE, "chave.contains=" + UPDATED_CHAVE);
    }

    @Test
    @Transactional
    void getAllApidiaexcepcionalsByChaveNotContainsSomething() throws Exception {
        // Initialize the database
        insertedApidiaexcepcional = apidiaexcepcionalRepository.saveAndFlush(apidiaexcepcional);

        // Get all the apidiaexcepcionalList where chave does not contain
        defaultApidiaexcepcionalFiltering("chave.doesNotContain=" + UPDATED_CHAVE, "chave.doesNotContain=" + DEFAULT_CHAVE);
    }

    private void defaultApidiaexcepcionalFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultApidiaexcepcionalShouldBeFound(shouldBeFound);
        defaultApidiaexcepcionalShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApidiaexcepcionalShouldBeFound(String filter) throws Exception {
        restApidiaexcepcionalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apidiaexcepcional.getId().intValue())))
            .andExpect(jsonPath("$.[*].dadosdiaexcepcional").value(hasItem(DEFAULT_DADOSDIAEXCEPCIONAL)))
            .andExpect(jsonPath("$.[*].chave").value(hasItem(DEFAULT_CHAVE)));

        // Check, that the count call also returns 1
        restApidiaexcepcionalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApidiaexcepcionalShouldNotBeFound(String filter) throws Exception {
        restApidiaexcepcionalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApidiaexcepcionalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApidiaexcepcional() throws Exception {
        // Get the apidiaexcepcional
        restApidiaexcepcionalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApidiaexcepcional() throws Exception {
        // Initialize the database
        insertedApidiaexcepcional = apidiaexcepcionalRepository.saveAndFlush(apidiaexcepcional);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apidiaexcepcional
        Apidiaexcepcional updatedApidiaexcepcional = apidiaexcepcionalRepository.findById(apidiaexcepcional.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedApidiaexcepcional are not directly saved in db
        em.detach(updatedApidiaexcepcional);
        updatedApidiaexcepcional.dadosdiaexcepcional(UPDATED_DADOSDIAEXCEPCIONAL).chave(UPDATED_CHAVE);

        restApidiaexcepcionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApidiaexcepcional.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedApidiaexcepcional))
            )
            .andExpect(status().isOk());

        // Validate the Apidiaexcepcional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedApidiaexcepcionalToMatchAllProperties(updatedApidiaexcepcional);
    }

    @Test
    @Transactional
    void putNonExistingApidiaexcepcional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apidiaexcepcional.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApidiaexcepcionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apidiaexcepcional.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apidiaexcepcional))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apidiaexcepcional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApidiaexcepcional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apidiaexcepcional.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApidiaexcepcionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apidiaexcepcional))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apidiaexcepcional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApidiaexcepcional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apidiaexcepcional.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApidiaexcepcionalMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apidiaexcepcional))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apidiaexcepcional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApidiaexcepcionalWithPatch() throws Exception {
        // Initialize the database
        insertedApidiaexcepcional = apidiaexcepcionalRepository.saveAndFlush(apidiaexcepcional);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apidiaexcepcional using partial update
        Apidiaexcepcional partialUpdatedApidiaexcepcional = new Apidiaexcepcional();
        partialUpdatedApidiaexcepcional.setId(apidiaexcepcional.getId());

        partialUpdatedApidiaexcepcional.dadosdiaexcepcional(UPDATED_DADOSDIAEXCEPCIONAL);

        restApidiaexcepcionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApidiaexcepcional.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApidiaexcepcional))
            )
            .andExpect(status().isOk());

        // Validate the Apidiaexcepcional in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApidiaexcepcionalUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedApidiaexcepcional, apidiaexcepcional),
            getPersistedApidiaexcepcional(apidiaexcepcional)
        );
    }

    @Test
    @Transactional
    void fullUpdateApidiaexcepcionalWithPatch() throws Exception {
        // Initialize the database
        insertedApidiaexcepcional = apidiaexcepcionalRepository.saveAndFlush(apidiaexcepcional);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apidiaexcepcional using partial update
        Apidiaexcepcional partialUpdatedApidiaexcepcional = new Apidiaexcepcional();
        partialUpdatedApidiaexcepcional.setId(apidiaexcepcional.getId());

        partialUpdatedApidiaexcepcional.dadosdiaexcepcional(UPDATED_DADOSDIAEXCEPCIONAL).chave(UPDATED_CHAVE);

        restApidiaexcepcionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApidiaexcepcional.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApidiaexcepcional))
            )
            .andExpect(status().isOk());

        // Validate the Apidiaexcepcional in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApidiaexcepcionalUpdatableFieldsEquals(
            partialUpdatedApidiaexcepcional,
            getPersistedApidiaexcepcional(partialUpdatedApidiaexcepcional)
        );
    }

    @Test
    @Transactional
    void patchNonExistingApidiaexcepcional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apidiaexcepcional.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApidiaexcepcionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apidiaexcepcional.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apidiaexcepcional))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apidiaexcepcional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApidiaexcepcional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apidiaexcepcional.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApidiaexcepcionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apidiaexcepcional))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apidiaexcepcional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApidiaexcepcional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apidiaexcepcional.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApidiaexcepcionalMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apidiaexcepcional))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apidiaexcepcional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApidiaexcepcional() throws Exception {
        // Initialize the database
        insertedApidiaexcepcional = apidiaexcepcionalRepository.saveAndFlush(apidiaexcepcional);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the apidiaexcepcional
        restApidiaexcepcionalMockMvc
            .perform(delete(ENTITY_API_URL_ID, apidiaexcepcional.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return apidiaexcepcionalRepository.count();
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

    protected Apidiaexcepcional getPersistedApidiaexcepcional(Apidiaexcepcional apidiaexcepcional) {
        return apidiaexcepcionalRepository.findById(apidiaexcepcional.getId()).orElseThrow();
    }

    protected void assertPersistedApidiaexcepcionalToMatchAllProperties(Apidiaexcepcional expectedApidiaexcepcional) {
        assertApidiaexcepcionalAllPropertiesEquals(expectedApidiaexcepcional, getPersistedApidiaexcepcional(expectedApidiaexcepcional));
    }

    protected void assertPersistedApidiaexcepcionalToMatchUpdatableProperties(Apidiaexcepcional expectedApidiaexcepcional) {
        assertApidiaexcepcionalAllUpdatablePropertiesEquals(
            expectedApidiaexcepcional,
            getPersistedApidiaexcepcional(expectedApidiaexcepcional)
        );
    }
}
