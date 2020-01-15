package com.design.inter.filter;

public interface Filter {
    /**
     * 处理类
     * @return
     */
    public Response doFilter(FilterChain filterChain);
}
