/**
  * Copyright 2021 bejson.com 
  */
package com.lulu.hotsearch.bean;
import java.util.List;

/**
 * Auto-generated: 2021-02-16 12:42:44
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class FilterRule extends BaseBean{
    private String filter;
    private List<String> rules;
    public void setFilter(String filter) {
         this.filter = filter;
     }
     public String getFilter() {
         return filter;
     }

    public void setRules(List<String> rules) {
         this.rules = rules;
     }
     public List<String> getRules() {
         return rules;
     }

}