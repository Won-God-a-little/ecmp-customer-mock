package cn.eqxiu.mock.domain.entity;

/**
 * 集成页面信息
 */
public class MockPage {
    private Long pageId;
    private String pageUrl;
    private String pageName;
    private String pageAges;


    public MockPage() {
    }

    public MockPage(Long pageId, String pageUrl, String pageName, String pageAges) {
        this.pageId = pageId;
        this.pageUrl = pageUrl;
        this.pageName = pageName;
        this.pageAges = pageAges;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageAges() {
        return pageAges;
    }

    public void setPageAges(String pageAges) {
        this.pageAges = pageAges;
    }

    @Override
    public String toString() {
        return "MockPage{" +
                "pageId=" + pageId +
                ", pageUrl='" + pageUrl + '\'' +
                ", pageName='" + pageName + '\'' +
                ", pageAges='" + pageAges + '\'' +
                '}';
    }
}
