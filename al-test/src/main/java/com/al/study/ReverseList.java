package com.al.study;

public class ReverseList {
    public class Node<T> {
        private Node nextNode;
        private T data;

        public void ResverList(Node nextNode, T data) {
            this.nextNode = nextNode;
            this.data = data;
        }

        public void setNextNode(Node nextNode){
            this.nextNode=nextNode;
        }

        public void setData(T data){
            this.data=data;
        }

        public Node getNextNode() {
            return nextNode;
        }

        public T getData() {
            return data;
        }
    }

    public static void main(String[] args){

    }

    public Node reverseListNode(Node head){
        if(head==null||head.getNextNode()==null){
           return head;
        }
        Node curNode=head;
        Node preNode=null;
        while(curNode!=null){
            curNode.setNextNode(preNode);
            preNode=curNode;
            Node nextNode=curNode.getNextNode();
            curNode=nextNode;
        }
        return preNode;
    }


}
