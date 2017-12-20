package com.actitime.utils;

public class ServiceResponse {
    private Object data;
    private Metadata metadata;
    private Object errors;

    public ServiceResponse(Object data, Metadata metadata, Object errors) {
        this.data = data;
        this.metadata = metadata;
        this.errors = errors;
    }

    public Object getData() {
        return data;
    }

    public Object getErrors() {
        return errors;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public static class Metadata {
        private final int totalCount;
        private final int limit;
        private final int offset;

        public Metadata(int totalCount, int limit, int offset) {
            this.totalCount = totalCount;
            this.limit = limit;
            this.offset = offset;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public int getLimit() {
            return limit;
        }

        public int getOffset() {
            return offset;
        }
    }
}
