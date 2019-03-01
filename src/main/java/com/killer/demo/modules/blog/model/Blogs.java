package com.killer.demo.modules.blog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.Data;
import org.bouncycastle.util.Arrays;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Blogs {
    private String id;

    private String title;

    private String author;

    private String tags;

    private String categoryId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date intime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    private String content;

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 方法一，将链表中数据转为整
        List a = new ArrayList<>(), b = new ArrayList<>();
        ListNode currentNode = l1;
        while(currentNode.next != null) {
            a.add(currentNode.val);
            currentNode = currentNode.next;
        }

        currentNode = l2;
        while(currentNode.next != null) {
            a.add(currentNode.val);
            currentNode = currentNode.next;
        }

        Integer[] objects = (Integer[])a.toArray();
        Integer[] objects1 = (Integer[])b.toArray();

        int[] a1 = new int[]{}, b1 = new int[]{};


//        Arrays.reverse(objects);
        return null;
    }

    public class ListNode {
       int val;
       ListNode next;
       ListNode(int x) { val = x; }
   }
}