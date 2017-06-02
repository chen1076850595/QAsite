package com.zhihu.QAsite.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

/**
 * Created by machenike on 2017/5/21 0021.
 */
//算法题演练
public class solution {
    //两个递增链表合成新的非递减链表
    public static ListNode Merge(ListNode list1,ListNode list2) {
        ListNode p,head;
        if(list1==null&&list2==null){
            return list1;
        }
        else if(list1==null&&list2!=null){
            head = list2;
        }
        else if(list1!=null&&list2==null){
            head = list1;
        }
        else{
            if(list1.val<list2.val){
                head = list1;
                list1 = list1.next;
            }
            else{
                head = list2;
                list2 = list2.next;
            }
        }
        p = head;

        while(list1!=null||list2!=null){
            if(list1!=null&&list2!=null){
                if(list1.val < list2.val){
                    p.next = list1;
                    p = p.next;
                    list1 = list1.next;
                }
                else{
                    p.next = list2;
                    p = p.next;
                    list2 = list2.next;
                }
            }
            else if(list1!=null&&list2==null){
                p.next = list1;
                p = p.next;
                list1 = list1.next;
            }
            else {
                p.next = list2;
                p = p.next;
                list2 = list2.next;
            }
        }
        return head;
    }
    public static ListNode createListNode(int n){
        ListNode listNode = new ListNode(n-1);
        ListNode p = listNode ;
        for(int i = n;i<n+10;i++){
            p.next = new ListNode(i);
            p = p.next;
        }
        return listNode;
    }
    public  static void main(String args []){

        ListNode result ;
        ListNode list1 = createListNode(0);
        ListNode list2 = createListNode(5);
        long start = System.currentTimeMillis();
        result = Merge(list1,list2);
//        try{
//       sleep(5000);}
//       catch (Exception e){
//            System.out.println(e);
//       }
        long end = System.currentTimeMillis();
        System.out.println("当前时间啊，"+start+" 运行完的时间" + end);

        while(result!=null){
            System.out.println(result.val);
            result = result.next;
        }

    }


}
