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

package org.openapitools.client.models


import com.squareup.moshi.Json

/**
 * 
 *
 * @param bepa 
 * @param cepa 
 * @param depa 
 * @param epa 
 * @param fepa 
 * @param gepa 
 */

data class Apa (

    @Json(name = "bepa")
    val bepa: java.math.BigDecimal = java.math.BigDecimal("0"),

    @Json(name = "cepa")
    val cepa: java.math.BigDecimal = java.math.BigDecimal("6.28318"),

    @Json(name = "depa")
    val depa: java.math.BigDecimal? = java.math.BigDecimal("71"),

    @Json(name = "epa")
    val epa: java.math.BigDecimal? = java.math.BigDecimal("-71"),

    @Json(name = "fepa")
    @Deprecated(message = "This property is deprecated.")
    val fepa: java.math.BigDecimal? = java.math.BigDecimal("100"),

    @Json(name = "gepa")
    val gepa: java.math.BigDecimal? = null

)

