/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package org.openapitools.client.apis

import org.openapitools.client.models.ModelApiResponse
import org.openapitools.client.models.Pet

import org.openapitools.client.infrastructure.*
import io.ktor.client.HttpClientConfig
import io.ktor.client.request.forms.formData
import io.ktor.client.engine.HttpClientEngine
import kotlinx.serialization.json.Json
import io.ktor.http.ParametersBuilder
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

open class PetApi(
    baseUrl: String = ApiClient.BASE_URL,
    httpClientEngine: HttpClientEngine? = null,
    httpClientConfig: ((HttpClientConfig<*>) -> Unit)? = null,
    jsonSerializer: Json = ApiClient.JSON_DEFAULT
) : ApiClient(baseUrl, httpClientEngine, httpClientConfig, jsonSerializer) {

    /**
     * Add a new pet to the store
     * 
     * @param body Pet object that needs to be added to the store
     * @return void
     */
    open suspend fun addPet(body: Pet): HttpResponse<Unit> {

        val localVariableAuthNames = listOf<String>("petstore_auth")

        val localVariableBody = body

        val localVariableQuery = mutableMapOf<String, List<String>>()
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.POST,
            "/pet",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
        )

        return jsonRequest(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames
        ).wrap()
    }



    /**
     * Deletes a pet
     * 
     * @param petId Pet id to delete
     * @param apiKey  (optional)
     * @return void
     */
    open suspend fun deletePet(petId: kotlin.Long, apiKey: kotlin.String? = null): HttpResponse<Unit> {

        val localVariableAuthNames = listOf<String>("petstore_auth")

        val localVariableBody = 
            io.ktor.client.utils.EmptyContent

        val localVariableQuery = mutableMapOf<String, List<String>>()
        val localVariableHeaders = mutableMapOf<String, String>()
        apiKey?.apply { localVariableHeaders["api_key"] = this.toString() }

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.DELETE,
            "/pet/{petId}".replace("{" + "petId" + "}", "$petId"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
        )

        return request(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames
        ).wrap()
    }



    /**
     * enum for parameter status
     */
    @Serializable
    enum class StatusFindPetsByStatus(val value: kotlin.String) {
        
        @SerialName(value = "available")
        available("available"),
        
        @SerialName(value = "pending")
        pending("pending"),
        
        @SerialName(value = "sold")
        sold("sold")
        
    }

    /**
     * Finds Pets by status
     * Multiple status values can be provided with comma separated strings
     * @param status Status values that need to be considered for filter
     * @return kotlin.collections.List<Pet>
     */
    @Suppress("UNCHECKED_CAST")
    open suspend fun findPetsByStatus(status: kotlin.collections.List<StatusFindPetsByStatus>): HttpResponse<kotlin.collections.List<Pet>> {

        val localVariableAuthNames = listOf<String>("petstore_auth")

        val localVariableBody = 
            io.ktor.client.utils.EmptyContent

        val localVariableQuery = mutableMapOf<String, List<String>>()
        status?.apply { localVariableQuery["status"] = toMultiValue(this, "csv") }
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/pet/findByStatus",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
        )

        return request(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames
        ).wrap<FindPetsByStatusResponse>().map { value }
    }

    @Serializable
    private class FindPetsByStatusResponse(val value: List<Pet>) {
        @Serializer(FindPetsByStatusResponse::class)
        companion object : KSerializer<FindPetsByStatusResponse> {
            private val serializer: KSerializer<List<Pet>> = serializer<List<Pet>>()
            override val descriptor = serializer.descriptor
            override fun serialize(encoder: Encoder, obj: FindPetsByStatusResponse) = serializer.serialize(encoder, obj.value)
            override fun deserialize(decoder: Decoder) = FindPetsByStatusResponse(serializer.deserialize(decoder))
        }
    }

    /**
     * Finds Pets by tags
     * Multiple tags can be provided with comma separated strings. Use tag1, tag2, tag3 for testing.
     * @param tags Tags to filter by
     * @return kotlin.collections.List<Pet>
     */
    @Suppress("UNCHECKED_CAST")
    open suspend fun findPetsByTags(tags: kotlin.collections.List<kotlin.String>): HttpResponse<kotlin.collections.List<Pet>> {

        val localVariableAuthNames = listOf<String>("petstore_auth")

        val localVariableBody = 
            io.ktor.client.utils.EmptyContent

        val localVariableQuery = mutableMapOf<String, List<String>>()
        tags?.apply { localVariableQuery["tags"] = toMultiValue(this, "csv") }
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/pet/findByTags",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
        )

        return request(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames
        ).wrap<FindPetsByTagsResponse>().map { value }
    }

    @Serializable
    private class FindPetsByTagsResponse(val value: List<Pet>) {
        @Serializer(FindPetsByTagsResponse::class)
        companion object : KSerializer<FindPetsByTagsResponse> {
            private val serializer: KSerializer<List<Pet>> = serializer<List<Pet>>()
            override val descriptor = serializer.descriptor
            override fun serialize(encoder: Encoder, obj: FindPetsByTagsResponse) = serializer.serialize(encoder, obj.value)
            override fun deserialize(decoder: Decoder) = FindPetsByTagsResponse(serializer.deserialize(decoder))
        }
    }

    /**
     * Find pet by ID
     * Returns a single pet
     * @param petId ID of pet to return
     * @return Pet
     */
    @Suppress("UNCHECKED_CAST")
    open suspend fun getPetById(petId: kotlin.Long): HttpResponse<Pet> {

        val localVariableAuthNames = listOf<String>("api_key")

        val localVariableBody = 
            io.ktor.client.utils.EmptyContent

        val localVariableQuery = mutableMapOf<String, List<String>>()
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/pet/{petId}".replace("{" + "petId" + "}", "$petId"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
        )

        return request(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames
        ).wrap()
    }


    /**
     * Update an existing pet
     * 
     * @param body Pet object that needs to be added to the store
     * @return void
     */
    open suspend fun updatePet(body: Pet): HttpResponse<Unit> {

        val localVariableAuthNames = listOf<String>("petstore_auth")

        val localVariableBody = body

        val localVariableQuery = mutableMapOf<String, List<String>>()
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.PUT,
            "/pet",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
        )

        return jsonRequest(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames
        ).wrap()
    }



    /**
     * Updates a pet in the store with form data
     * 
     * @param petId ID of pet that needs to be updated
     * @param name Updated name of the pet (optional)
     * @param status Updated status of the pet (optional)
     * @return void
     */
    open suspend fun updatePetWithForm(petId: kotlin.Long, name: kotlin.String? = null, status: kotlin.String? = null): HttpResponse<Unit> {

        val localVariableAuthNames = listOf<String>("petstore_auth")

        val localVariableBody = 
            ParametersBuilder().also {
                name?.apply { it.append("name", name.toString()) }
                status?.apply { it.append("status", status.toString()) }
            }.build()

        val localVariableQuery = mutableMapOf<String, List<String>>()
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.POST,
            "/pet/{petId}".replace("{" + "petId" + "}", "$petId"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
        )

        return urlEncodedFormRequest(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames
        ).wrap()
    }


    /**
     * uploads an image
     * 
     * @param petId ID of pet to update
     * @param additionalMetadata Additional data to pass to server (optional)
     * @param file file to upload (optional)
     * @return ModelApiResponse
     */
    @Suppress("UNCHECKED_CAST")
    open suspend fun uploadFile(petId: kotlin.Long, additionalMetadata: kotlin.String? = null, file: io.ktor.client.request.forms.InputProvider? = null): HttpResponse<ModelApiResponse> {

        val localVariableAuthNames = listOf<String>("petstore_auth")

        val localVariableBody = 
            formData {
                additionalMetadata?.apply { append("additionalMetadata", additionalMetadata) }
                file?.apply { append("file", file) }
            }

        val localVariableQuery = mutableMapOf<String, List<String>>()
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.POST,
            "/pet/{petId}/uploadImage".replace("{" + "petId" + "}", "$petId"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
        )

        return multipartFormRequest(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames
        ).wrap()
    }


}
