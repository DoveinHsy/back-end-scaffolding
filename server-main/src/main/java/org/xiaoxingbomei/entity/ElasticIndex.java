package org.xiaoxingbomei.entity;

import java.io.Serializable;

/**
 *
 */
public class ElasticIndex implements Serializable
{

    private String indexName;

    public ElasticIndex(String name) {
        this.indexName = name;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}
