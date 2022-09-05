/*
    Interlink Configuration Management Database
    © 2018-Present - SouthWinds Tech Ltd - www.southwinds.io

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

    Contributors to this project, hereby assign copyright in their code to the
    project, to be licensed under the same terms as the rest of the code.
*/
package io.southwinds.interlink.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.List;

@ApiModel(
    description = "Defines the type of a link."
)
public class LinkTypeData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String key;
    private String name;
    private String description;
    private JSONObject attrValid;
    private JSONObject metaSchema;
    private List<String> tag;
    private boolean encryptMeta;
    private boolean encryptTxt;
    private JSONObject style;
    private String created;
    private String updated;
    private Integer version;
    private String changedBy;
    private String modelKey;

    public LinkTypeData() {
    }

    @ApiModelProperty(
        position = 0,
        required = true,
        value = "The natural key that uniquely identifies this type of link.",
        example = "test_link_type"
    )
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @ApiModelProperty(
            position = 1,
            required = true,
            value = "The natural key of the model this link type is in.",
            example = "test_model"
    )
    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    @ApiModelProperty(
        position = 2,
        required = true,
        value = "The name of the link type (unique).",
        example = "Test Link"
    )
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ApiModelProperty(
        position = 3,
        required = false,
        value = "The link type description.",
        example = "This is a link type for testing purposes."
    )
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ApiModelProperty(
        position = 4,
        required = false,
        value = "A key/value pair dictionary used to define constraints for attribute values in links of this type." +
                "The possible options for validation are: a) required: mandatory, b) allowed: not mandatory c) left empty if no validation is required.",
        example = "{\n" +
                "    \"WBS\": \"required\",\n" +
                "    \"COMPANY\": \"allowed\"\n" +
                "  }"
    )
    public JSONObject getAttrValid() {
        return attrValid;
    }

    public void setAttrValid(JSONObject attribute) {
        this.attrValid = attribute;
    }

    @ApiModelProperty(
        position = 5,
        required = false,
        value = "The date and time on which the link type was created.",
        example = "17-02-2016 15:23:34"
    )
    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @ApiModelProperty(
        position = 6,
        required = false,
        value = "The date and time on which the link type was last updated.",
        example = "16-06-2017 17:56:31"
    )
    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @ApiModelProperty(
        position = 7,
        required = false,
        value = "The version number for the link type.",
        example = "4"
    )
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @ApiModelProperty(
        position = 8,
        required = false,
        value = "The user that made the change.",
        example = "admin"
    )
    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    @ApiModelProperty(
        position = 9,
        required = false,
        value = "The JSON Schema used to validate the meta attribute of links of this type." +
                "If specified, links of this type have to have a meta attribute that passes the validation against this schema."
    )
    public JSONObject getMetaSchema() {
        return metaSchema;
    }

    public void setMetaSchema(JSONObject metaSchema) {
        this.metaSchema = metaSchema;
    }

    @ApiModelProperty(
            position = 10,
            required = false,
            value = "An array of strings used as tags for filtering search results. " +
                    "The value of each tag is arbitrary and depends on how searches on the item will be made.",
            example = "[ 'VM', 'AMD64', 'EUROPE' ]",
            allowEmptyValue = true
    )
    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    @ApiModelProperty(
            position = 11,
            required = false,
            value = "Indicates whether the Meta field in an Configuration Link should be encrypted in the database.",
            example = "true",
            allowEmptyValue = true
    )
    public boolean getEncryptMeta() {
        return encryptMeta;
    }

    public void setEncryptMeta(boolean encryptMeta) {
        this.encryptMeta = encryptMeta;
    }

    @ApiModelProperty(
            position = 12,
            required = false,
            value = "Indicates whether the Txt field in an Configuration Link should be encrypted in the database.",
            example = "true",
            allowEmptyValue = true
    )
    public boolean getEncryptTxt() {
        return encryptTxt;
    }

    public void setEncryptTxt(boolean encryptTxt) {
        this.encryptTxt = encryptTxt;
    }

    @ApiModelProperty(
            position = 13,
            required = false,
            value = "A json object that store style information for the UI to render the link type.",
            allowEmptyValue = true
    )
    public JSONObject getStyle() {
        return style;
    }

    public void setStyle(JSONObject style) {
        this.style = style;
    }
}
