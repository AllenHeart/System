package com.atguigu.demo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;
/**
 * @author
 * @since 2018/12/13
 */
public class CodeGenerator {

    @Test
    public void run() {

        // 1、创建代码生成器  代码生产器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();

        //projectPath 得到当前文件夹的路径 相对路径
        String projectPath = System.getProperty("user.dir");

        //ProjectPath 所以根据自己的需要修改自己的绝对路径
        gc.setOutputDir("/Users/huanghao/projct/guli_parent/service/service_edu" + "/src/main/java");

        gc.setAuthor("testjava"); //代码生成后的作者  注释

        gc.setOpen(false); //生成后是否打开资源管理器 代码生成后代码是否打开还是不打开
        gc.setFileOverride(false); //重新生成时文件是否覆盖
        gc.setServiceName("%sService");	//去掉Service接口的首字母I

        gc.setIdType(IdType.ID_WORKER_STR); //主键策略如果实体类的类型是 Long 类型 就要 ID_WORKER. 如果用的是字符串类型用是 ID_WORKER_STR
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(true);//开启Swagger2模式

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/guli?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        //包: 代码生成的存放位置 com.atguigu.eduservice
        pc.setParent("com.atguigu");

        pc.setModuleName("eduservice"); //模块名

        //包: 代码生成的存放位置 com.atguigu.eduservice.com.atguigu.vod.controller
        pc.setController("controller");

        //包: 代码生成的存放位置 com.atguigu.eduservice.entity
        pc.setEntity("entity");

        //包: 代码生成的存放位置 com.atguigu.eduservice.com.atguigu.vod.service
        pc.setService("service");

        //包: 代码生成的存放位置 com.atguigu.eduservice.mapper
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置 逆向工程使用该策略配置
        StrategyConfig strategy = new StrategyConfig();

        strategy.setInclude("edu_comment"); //根据数据库的表逆向生成对应的 mapper 接口可多添加表结构

        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        mpg.setStrategy(strategy);


        // 6、执行
        mpg.execute();
    }
}
