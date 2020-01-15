package com.design.inter.filter;

public class SensitiveFilter implements Filter {
    @Override
    public Response doFilter(FilterChain filterChain) {

        Response response=filterChain.doFilter(filterChain);
        return response;
    }
}
