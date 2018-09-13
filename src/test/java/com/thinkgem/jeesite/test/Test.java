package com.thinkgem.jeesite.test;

public class Test {

	public static void main(String[] args) {
		//测试文件
        /*String a="[{&quot;bomName&quot;:&quot;RB521CS-30&quot;,&quot;bomType&quot;:&quot;二极管&quot;,&quot;counts&quot;:&quot;10989&quot;,&quot;stockCounts&quot;:&quot;4&quot;},{&quot;bomName&quot;:&quot;0402 1VF 16V&quot;,&quot;bomType&quot;:&quot;电容&quot;,&quot;counts&quot;:&quot;1221&quot;,&quot;stockCounts&quot;:&quot;2&quot;},{&quot;bomName&quot;:&quot;0402 1VF 10V&quot;,&quot;bomType&quot;:&quot;电容&quot;,&quot;counts&quot;:&quot;2442&quot;,&quot;stockCounts&quot;:&quot;40&quot;},{&quot;bomName&quot;:&quot;0402 100K&quot;,&quot;bomType&quot;:&quot;电阻&quot;,&quot;counts&quot;:&quot;3663&quot;,&quot;stockCounts&quot;:&quot;542&quot;},{&quot;bomName&quot;:&quot;0402 0R&quot;,&quot;bomType&quot;:&quot;电阻&quot;,&quot;counts&quot;:&quot;6105&quot;,&quot;stockCounts&quot;:&quot;66&quot;}]";
        String appJson = StringEscapeUtils.unescapeHtml(a);
        System.out.println(appJson);*/
       String a=Thread.currentThread().getContextClassLoader().getResource("jasper/").getPath();
        System.out.println(a);
    }
	
}
