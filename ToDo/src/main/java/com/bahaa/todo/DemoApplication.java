package com.bahaa.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
class Solution {
    public List<Integer> replaceNonCoprimes(int[] nums) {
        Stack<Integer> stack = new Stack<Integer>();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length-1; i++) {
            if(!stack.isEmpty() && gcd(stack.peek(), nums[i]) != 1){
                int lcm = lcm(stack.pop(), nums[i]);
               list.add(lcm);
               nums[i] = lcm;
               i--;
            }
            else{
                if(gcd(nums[i], nums[i+1])!=1){
                    nums[i] = lcm(nums[i], nums[i+1]);
                    i--;
                }
                else stack.push(nums[i]);
            }
        }
        List<Integer> result = List.of(nums[nums.length-1]);
        result.addAll(nums.length-1, list);
        return result;
    }
    public int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    public int lcm(int a, int b) {
        return (a / gcd(a, b)) * b; // Avoid overflow
    }
}
