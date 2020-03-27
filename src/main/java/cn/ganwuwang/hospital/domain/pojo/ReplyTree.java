package cn.ganwuwang.hospital.domain.pojo;

import java.util.List;

public class ReplyTree{

    private Long id;
    private String label;
    private Reply value;
    private List<ReplyTree> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Reply getValue() {
        return value;
    }

    public void setValue(Reply value) {
        this.value = value;
    }

    public List<ReplyTree> getChildren() {
        return children;
    }

    public void setChildren(List<ReplyTree> children) {
        this.children = children;
    }
}
