package com.al.study;

public class shuangxianglianbaiofanzhuan {
    public static void main(String[] args){
        DoubleLinkedList<Integer> list=new DoubleLinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
//        System.out.println("初始链表的头："+list.getFirst());
//        System.out.println("初始链表的尾："+list.getLast());
        System.out.println("初始双向链表为：");
        list.printList();
        list.reverseBySwapata();
//        System.out.println("反转后链表的头："+list.getFirst());
//        System.out.println("反转后链表的尾："+list.getLast());
        System.out.println("反转后的链表为：");
        list.printList();
    }
}
class DoubleLinkedList<E>{
    Node<E> head;
    Node<E> tail;
    int size=0;
    //定义Node节点类，包含当前节点值item，指向前后节点的指针（节点变量）
    private static class Node<E>{
        E item;
        Node<E> pre;
        Node<E> next;

        public Node(E item, Node<E> pre, Node<E> next) {
            this.item = item;
            this.pre = pre;
            this.next = next;
        }
    }
    public void add(E item){
        //l节点每次都指向当前链表的尾
        Node<E> l=tail;
        Node<E> node=new Node<E>(item,l,null);
        tail=node;
        if(l==null){
            //将head指向第一个节点
            head=node;
        }else {
            //在当前链表的尾添加新的节点
            l.next=node;
        }
        size++;
    }
    public E getFirst(){
        return head!=null?head.item:null;
    }
    public E getLast(){
        return tail!=null?tail.item:null;
    }
    public E get(int index){
        if(index>size-1 || index<0){
            throw new ArrayIndexOutOfBoundsException();
        }
        if(index<(size>>1)){
            Node<E> node=head;
            for(int i=0;i<index;i++){
                node=node.next;
            }
            return node!=null?node.item:null;
        }else {
            Node<E> node=tail;
            for(int i=size-1;i>index;i--){
                node=node.pre;
            }
            return node!=null?node.item:null;
        }
    }
    void printList(){
        Node<E> node=head;
        while (node!=null){
            System.out.print(node.item+"->");
            node=node.next;
        }
        System.out.println("null");
    }
    //利用指针进行链表反转
    public void reverseBySwapPointer(){
        //定义临时节点指针，初始化值为null
        Node<E> temp=null;
        //定义当前节点指针，初始化指向head节点
        Node<E> current=head;
        //定义tail指针，初始化指向head
        tail=head;
        while (current!=null){
            //temp指向当前节点的前一个节点（初始时head的前一个节点为null）
            temp=current.pre;
            //当前节点的前向指针指向当前节点的下一个节点
            current.pre=current.next;
            //当前节点的next指针指向临时节点指针（temp初始时为null）
            current.next=temp;
            //当前节点指针current指向下一个节点（往后移动）
            current=current.pre;
        }
        //循环结束后temp指向第二个节点，利用temp的前向指针（pre）指向第一个节点，将head指向反转后链表的第一个节点
        if(temp!=null){
            head=temp.pre;
        }
    }
    //不使用指针，直接采用值交换方法进行链表反转
    public void reverseBySwapata(){
        Node<E> headNode=head;
        Node<E> tailNode=tail;
        //只要前向指针与后向指针不指向同一个节点，循环交换
        while (headNode!=tailNode){
            //定义存储节点数据的中间变量
            E data=headNode.item;
//            System.out.println("headNode:"+data);
            //交换前向指针所指节点的值与后向节点指针指向节点的值
            headNode.item=tailNode.item;
//            System.out.println("tailNode:"+tailNode.item);
            tailNode.item=data;
            //前向节点指针向后移动
            headNode=headNode.next;
            //如果前向指针和后向指针不指向同一个节点，则将后向指针向前移动
            if(headNode==tailNode){
                break;
            }
            tailNode=tailNode.pre;
        }
    }
}
