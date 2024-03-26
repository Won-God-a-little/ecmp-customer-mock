package cn.eqxiu.mock.domain.entity;

import java.util.List;

/**
 * 集成页面信息集合
 */
public class MockPageInfo {

    private Integer groupId;
    private String groupName;
    private List<MockPage> pages;

    public MockPageInfo() {
    }

    public MockPageInfo(Integer groupId, String groupName, List<MockPage> pages) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.pages = pages;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<MockPage> getPages() {
        return pages;
    }

    public void setPages(List<MockPage> pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "MockPageInfo{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", pages=" + pages +
                '}';
    }
}
