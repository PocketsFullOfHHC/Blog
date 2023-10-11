package com.hhc.blogs.req;

public class CollectSaveReq {
    private Long collectorId;
    private Long blogId;

    public Long getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(Long collectorId) {
        this.collectorId = collectorId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Collect{");
        sb.append(", collectorId=").append(collectorId);
        sb.append(", blogId=").append(blogId);
        sb.append('}');
        return sb.toString();
    }
}
