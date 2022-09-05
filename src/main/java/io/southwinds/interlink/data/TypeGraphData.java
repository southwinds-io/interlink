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
import java.util.ArrayList;
import java.util.List;

@ApiModel
public class TypeGraphData implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<ItemTypeData> itemTypes = new ArrayList<>();
    private List<LinkTypeData> linkTypes = new ArrayList<>();
    private List<LinkRuleData> linkRules = new ArrayList<>();

    public TypeGraphData() {
    }

    @ApiModelProperty(
        position = 0,
        required = true,
        value = "A list of item types which are part of the model.")
    public List<ItemTypeData> getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(List<ItemTypeData> itemTypes) {
        this.itemTypes = itemTypes;
    }

    @ApiModelProperty(
        position = 1,
        required = true,
        value = "A list of link types which are part of the model.")
    public List<LinkTypeData> getLinkTypes() {
        return linkTypes;
    }

    public void setLinkTypes(List<LinkTypeData> linkTypes) {
        this.linkTypes = linkTypes;
    }

    @ApiModelProperty(
        position = 2,
        required = true,
        value = "A list of link rules which are part of the model.")
    public List<LinkRuleData> getLinkRules() {
        return linkRules;
    }

    public void setLinkRules(List<LinkRuleData> linkRules) {
        this.linkRules = linkRules;
    }
}
