/*
OpenAPI Petstore

This spec is mainly for testing Petstore server and contains fake endpoints, models. Please do not use this for any other purpose. Special characters: \" \\

API version: 1.0.0
*/

// Code generated by OpenAPI Generator (https://openapi-generator.tech); DO NOT EDIT.

package petstore

import (
	"encoding/json"
)

// checks if the OneOfPrimitiveTypeChild type satisfies the MappedNullable interface at compile time
var _ MappedNullable = &OneOfPrimitiveTypeChild{}

// OneOfPrimitiveTypeChild struct for OneOfPrimitiveTypeChild
type OneOfPrimitiveTypeChild struct {
	Name *string `json:"name,omitempty"`
	AdditionalProperties map[string]interface{}
}

type _OneOfPrimitiveTypeChild OneOfPrimitiveTypeChild

// NewOneOfPrimitiveTypeChild instantiates a new OneOfPrimitiveTypeChild object
// This constructor will assign default values to properties that have it defined,
// and makes sure properties required by API are set, but the set of arguments
// will change when the set of required properties is changed
func NewOneOfPrimitiveTypeChild() *OneOfPrimitiveTypeChild {
	this := OneOfPrimitiveTypeChild{}
	return &this
}

// NewOneOfPrimitiveTypeChildWithDefaults instantiates a new OneOfPrimitiveTypeChild object
// This constructor will only assign default values to properties that have it defined,
// but it doesn't guarantee that properties required by API are set
func NewOneOfPrimitiveTypeChildWithDefaults() *OneOfPrimitiveTypeChild {
	this := OneOfPrimitiveTypeChild{}
	return &this
}

// GetName returns the Name field value if set, zero value otherwise.
func (o *OneOfPrimitiveTypeChild) GetName() string {
	if o == nil || IsNil(o.Name) {
		var ret string
		return ret
	}
	return *o.Name
}

// GetNameOk returns a tuple with the Name field value if set, nil otherwise
// and a boolean to check if the value has been set.
func (o *OneOfPrimitiveTypeChild) GetNameOk() (*string, bool) {
	if o == nil || IsNil(o.Name) {
		return nil, false
	}
	return o.Name, true
}

// HasName returns a boolean if a field has been set.
func (o *OneOfPrimitiveTypeChild) HasName() bool {
	if o != nil && !IsNil(o.Name) {
		return true
	}

	return false
}

// SetName gets a reference to the given string and assigns it to the Name field.
func (o *OneOfPrimitiveTypeChild) SetName(v string) {
	o.Name = &v
}

func (o OneOfPrimitiveTypeChild) MarshalJSON() ([]byte, error) {
	toSerialize,err := o.ToMap()
	if err != nil {
		return []byte{}, err
	}
	return json.Marshal(toSerialize)
}

func (o OneOfPrimitiveTypeChild) ToMap() (map[string]interface{}, error) {
	toSerialize := map[string]interface{}{}
	if !IsNil(o.Name) {
		toSerialize["name"] = o.Name
	}

	for key, value := range o.AdditionalProperties {
		toSerialize[key] = value
	}

	return toSerialize, nil
}

func (o *OneOfPrimitiveTypeChild) UnmarshalJSON(bytes []byte) (err error) {
	varOneOfPrimitiveTypeChild := _OneOfPrimitiveTypeChild{}

	err = json.Unmarshal(bytes, &varOneOfPrimitiveTypeChild)

	if err != nil {
		return err
	}

	*o = OneOfPrimitiveTypeChild(varOneOfPrimitiveTypeChild)

	additionalProperties := make(map[string]interface{})

	if err = json.Unmarshal(bytes, &additionalProperties); err == nil {
		delete(additionalProperties, "name")
		o.AdditionalProperties = additionalProperties
	}

	return err
}

type NullableOneOfPrimitiveTypeChild struct {
	value *OneOfPrimitiveTypeChild
	isSet bool
}

func (v NullableOneOfPrimitiveTypeChild) Get() *OneOfPrimitiveTypeChild {
	return v.value
}

func (v *NullableOneOfPrimitiveTypeChild) Set(val *OneOfPrimitiveTypeChild) {
	v.value = val
	v.isSet = true
}

func (v NullableOneOfPrimitiveTypeChild) IsSet() bool {
	return v.isSet
}

func (v *NullableOneOfPrimitiveTypeChild) Unset() {
	v.value = nil
	v.isSet = false
}

func NewNullableOneOfPrimitiveTypeChild(val *OneOfPrimitiveTypeChild) *NullableOneOfPrimitiveTypeChild {
	return &NullableOneOfPrimitiveTypeChild{value: val, isSet: true}
}

func (v NullableOneOfPrimitiveTypeChild) MarshalJSON() ([]byte, error) {
	return json.Marshal(v.value)
}

func (v *NullableOneOfPrimitiveTypeChild) UnmarshalJSON(src []byte) error {
	v.isSet = true
	return json.Unmarshal(src, &v.value)
}


