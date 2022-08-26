package com.example.generate;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.example.common.AutoIdEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * MyBatisPlus代码生成器
 * Created by macro on 2020/8/20.
 */
public class GenerateApplication {

    public static final String PREFIX = "/src/main/java/com/example/cloudmusic";
    public static final String SERVICE_PATH = System.getProperty("user.dir") + PREFIX + "/service/";
    public static final String SERVICE_IMPL_PATH = System.getProperty("user.dir") + PREFIX + "/service/impl/";
    public static final String MAPPER_PATH = System.getProperty("user.dir") + PREFIX + "/mapper/";
    public static final String ENTITY_PATH = System.getProperty("user.dir") + PREFIX + "/model/entity/";
    public static final String MAPPER_XML_PATH = System.getProperty("user.dir") + "/resources/mapper/";

    private static final String TABLE_FREIX = "t_";
    private static final String AUTHOR = "liyuan";

    public static void main(String[] args) {

        String projectPath = System.getProperty("user.dir");
        System.out.println("projectPah = " + projectPath);
        String[] tableNames = scanner("表名，多个英文逗号分割").split(",");
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(initGlobalConfig(projectPath));
        autoGenerator.setDataSource(initDataSourceConfig());
        autoGenerator.setPackageInfo(initPackageConfig());
        autoGenerator.setCfg(initInjectionConfig());
        autoGenerator.setTemplate(initTemplateConfig());
        autoGenerator.setStrategy(initStrategyConfig(tableNames));
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }

    /**
     * 读取控制台内容信息
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入" + tip + "："));
        if (scanner.hasNext()) {
            String next = scanner.next();
            if (StrUtil.isNotEmpty(next)) {
                return next;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 初始化全局配置
     *
     * @return
     */
    private static GlobalConfig initGlobalConfig(String projectPath) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor(AUTHOR);
        globalConfig.setOpen(false);
        globalConfig.setSwagger2(false);
        globalConfig.setBaseResultMap(false);
        // 运行文件覆盖
        globalConfig.setFileOverride(true);
        // Date映射
        globalConfig.setDateType(DateType.TIME_PACK);
        globalConfig.setEntityName("%s");
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setControllerName("%sController");
        return globalConfig;
    }

    /**
     * 初始化数据源配置
     */
    private static DataSourceConfig initDataSourceConfig() {
        Props props = new Props("database.properties");
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(props.getStr("dataSource.url"));
        dataSourceConfig.setDriverName(props.getStr("datasource.driverName"));
        dataSourceConfig.setUsername(props.getStr("dataSource.username"));
        dataSourceConfig.setPassword(props.getStr("dataSource.password"));
        dataSourceConfig.setDriverName(props.getStr("dataSource.driverName"));
        dataSourceConfig.setTypeConvert(new TinyintSqlTypeConvert());

        return dataSourceConfig;
    }

    /**
     * 初始化包配置
     */
    private static PackageConfig initPackageConfig() {

        Props props = new Props("database.properties");
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(props.getStr("package.base"));
        packageConfig.setEntity("model.entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");

        return packageConfig;
    }

    /**
     * 初始化模板配置
     */
    private static TemplateConfig initTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        // 可以对controller、service、entity模板进行配置
        templateConfig.setController(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setMapper(null);
        //mapper.xml模板需单独配置
        templateConfig.setXml(null);
        templateConfig.setEntity(null);
        return templateConfig;
    }

    /**
     * 初始化策略配置
     */
    private static StrategyConfig initStrategyConfig(String[] tableNames) {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setSuperEntityClass(AutoIdEntity.class);
        strategyConfig.setEntitySerialVersionUID(false);
        strategyConfig.setLogicDeleteFieldName("is_deleted");
        strategyConfig.setTablePrefix(TABLE_FREIX);

        strategyConfig.setSuperEntityColumns("id", "create_time", "update_time");
        // 当表名中带*号时可以启用通配符模式
        if (tableNames.length == 1 && tableNames[0].contains("*")) {
            String[] likeStr = tableNames[0].split("_");
            String likePrefix = likeStr[0] + "_";
            strategyConfig.setLikeTable(new LikeTable(likePrefix));
        } else {
            strategyConfig.setInclude(tableNames);
        }
        return strategyConfig;
    }

    /**
     * 初始化自定义配置
     */
    private static InjectionConfig initInjectionConfig() {
        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // 可用于自定义属性
            }
        };
        // 模板引擎是freemarker
        String mapperPath = "/templates/mapper.java.ftl";
        String templatePath = "/templates/mapper.xml.ftl";
        String servicePath = "/templates/service.java.ftl";
        String serviceImplPath = "/templates/serviceImpl.java.ftl";
        String entityPath = "/templates/baseEntity.java.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出

        focList.add(new FileOutConfig(entityPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义Entity文件名跟生成路径
                return ENTITY_PATH + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig(servicePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义Entity文件名跟生成路径
                return SERVICE_PATH + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig(serviceImplPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义Entity文件名跟生成路径
                return SERVICE_IMPL_PATH + tableInfo.getEntityName() + "ServiceImpl" + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig(mapperPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return MAPPER_PATH + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return MAPPER_XML_PATH + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });


        injectionConfig.setFileOutConfigList(focList);
        return injectionConfig;
    }

}