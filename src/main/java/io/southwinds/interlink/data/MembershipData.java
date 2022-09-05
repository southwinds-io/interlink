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

import java.io.Serializable;

@ApiModel
public class MembershipData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String key;
    private String userKey;
    private String roleKey;
    private String created;
    private String updated;
    private Integer version;
    private String changedBy;

    public MembershipData() {
    }

    public MembershipData(String key) {
        this.key = key;
    }

    @ApiModelProperty(
            position = 1,
            required = true,
            value = "The unique key for the membership."
    )
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @ApiModelProperty(
            position = 2,
            required = true,
            value = "The unique key of the user this membership is for."
    )
    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    @ApiModelProperty(
            position = 3,
            required = true,
            value = "The unique key of the role this membership is for."
    )
    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    @ApiModelProperty(
            position = 4,
            required = false,
            value = "The date on which the item was created.",
            notes = "This value is always autogenerated by the service upon item creation and does not need to be specified. " +
                    "It is subsequently retrieved every time item information is requested.",
            allowEmptyValue = true
    )
    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @ApiModelProperty(
            position = 5,
            required = false,
            value = "The date on which the item was last updated.",
            notes = "This value is always autogenerated by the service upon item updates and does not need to be specified. " +
                    "It is subsequently retrieved every time item information is requested."
    )
    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @ApiModelProperty(
            position = 6,
            required = false,
            value = "The item version.",
            notes = "This value is always autogenerated by the service upon item creation or updates. " +
                    "It only needs to be specified if optimistic concurrency is required, for example by a user interface service."
    )
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
}
