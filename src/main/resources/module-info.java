module havis.middleware.ale.api {
    requires havis.middleware.utils;
    requires havis.transport.api;

    requires transitive havis.util.monitor;
    requires transitive jackson.annotations;
    requires transitive java.logging;
    requires transitive java.rmi;
    requires transitive java.xml;
    requires transitive javax.ws.rs.api;
    requires transitive jaxb.api;
    requires transitive jaxws.api;
    requires transitive resteasy.jaxrs;

    exports havis.middleware.ale.base;
    exports havis.middleware.ale.base.annotation;
    exports havis.middleware.ale.base.exception;
    exports havis.middleware.ale.base.message;
    exports havis.middleware.ale.base.operation;
    exports havis.middleware.ale.base.operation.port;
    exports havis.middleware.ale.base.operation.port.result;
    exports havis.middleware.ale.base.operation.tag;
    exports havis.middleware.ale.base.operation.tag.result;
    exports havis.middleware.ale.base.po;
    exports havis.middleware.ale.base.report;
    exports havis.middleware.ale.client;
    exports havis.middleware.ale.config;
    exports havis.middleware.ale.config.service.mc;
    exports havis.middleware.ale.exit;
    exports havis.middleware.ale.exit.event;
    exports havis.middleware.ale.reader;
    exports havis.middleware.ale.service;
    exports havis.middleware.ale.service.ac;
    exports havis.middleware.ale.service.cc;
    exports havis.middleware.ale.service.doc;
    exports havis.middleware.ale.service.ec;
    exports havis.middleware.ale.service.lr;
    exports havis.middleware.ale.service.mc;
    exports havis.middleware.ale.service.pc;
    exports havis.middleware.ale.service.rc;
    exports havis.middleware.ale.service.tm;
    exports havis.middleware.ale.subscriber;

}