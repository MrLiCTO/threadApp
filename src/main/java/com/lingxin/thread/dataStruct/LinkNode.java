package com.lingxin.thread.dataStruct;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class LinkNode<T> {
    private T data;
    private LinkNode<T> preNode;
    private LinkNode<T> nextNode;

    public LinkNode(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkNode<?> linkNode = (LinkNode<?>) o;
        return Objects.equals(data, linkNode.data) &&
                Objects.equals(preNode, linkNode.preNode) &&
                Objects.equals(nextNode, linkNode.nextNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, preNode, nextNode);
    }
}
