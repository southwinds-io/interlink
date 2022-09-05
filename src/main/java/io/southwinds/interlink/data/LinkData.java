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

import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.List;

public class LinkData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String key;
    private String description;
    private String type;
    private List<String> tag;
    private JSONObject attribute;
    private JSONObject meta;
    private boolean metaEnc;
    private String txt;
    private boolean txtEnc;
    private short encKeyIx;
    private String startItemKey;
    private String endItemKey;
    private String created;
    private String updated;
    private Integer version;
    private String changedBy;

    public LinkData() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JSONObject getMeta() {
        return meta;
    }

    public void setMeta(JSONObject meta) {
        this.meta = meta;
    }

    public JSONObject getAttribute() {
        return attribute;
    }

    public void setAttribute(JSONObject attribute) {
        this.attribute = attribute;
    }

    public String getStartItemKey() {
        return startItemKey;
    }

    public void setStartItemKey(String startItemKey) {
        this.startItemKey = startItemKey;
    }

    public String getEndItemKey() {
        return endItemKey;
    }

    public void setEndItemKey(String endItemKey) {
        this.endItemKey = endItemKey;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public boolean isMetaEnc() {
        return metaEnc;
    }

    public void setMetaEnc(boolean metaEnc) {
        this.metaEnc = metaEnc;
    }

    public boolean isTxtEnc() {
        return txtEnc;
    }

    public void setTxtEnc(boolean txtEnc) {
        this.txtEnc = txtEnc;
    }

    public short getEncKeyIx() {
        return encKeyIx;
    }

    public void setEncKeyIx(short encKeyIx) {
        this.encKeyIx = encKeyIx;
    }
}