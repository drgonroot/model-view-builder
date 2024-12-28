package com.btye102.mvb.builder;

import com.btye102.mvb.proxy.method.PMethod;

import java.util.Map;

/**
 * @author: rd13
 * @since: 2024/12/23
 **/
public class BuildContext {

    private ModelViewBuilder modelViewBuilder;
    private Map<Class<?>, Map<String, PMethod>> viewProxyMethodMap;

    public BuildContext setModelViewBuilder(ModelViewBuilder modelViewBuilder) {
        if (this.modelViewBuilder == null) {
            this.modelViewBuilder = modelViewBuilder;
        }
        return this;
    }

    public BuildContext setViewProxyMethodMap(Map<Class<?>, Map<String, PMethod>> viewProxyMethodMap) {
        if (this.viewProxyMethodMap == null) {
            this.viewProxyMethodMap = viewProxyMethodMap;
        }
        return this;
    }

    public ModelViewBuilder getModelViewBuilder() {
        return modelViewBuilder;
    }

    public Map<Class<?>, Map<String, PMethod>> getViewProxyMethodMap() {
        return viewProxyMethodMap;
    }
}
