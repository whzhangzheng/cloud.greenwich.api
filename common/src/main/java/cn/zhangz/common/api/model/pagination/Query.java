package cn.zhangz.common.api.model.pagination;

import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    private int offset;
    private int limit;
    private String search;

    public Query(Map<String, Object> params) {
        this.putAll(params);
        if (params.get("offset") != null && params.get("limit") != null) {
            this.offset = Integer.parseInt(params.get("offset").toString());
            this.limit = Integer.parseInt(params.get("limit").toString());
            this.put("offset", this.offset);
            this.put("page", this.offset / this.limit + 1);
            this.put("limit", this.limit);
        }
        if(params.get("search") != null && params.get("search") != ""){
            this.search = params.get("search").toString();
            this.put("search", this.search);
        }

    }
    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search){
        this.search = search;
        this.put("search", search);
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
        this.put("offset", offset);
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
        this.put("limit", limit);
    }
}
