package util;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

// controller service mapper entity：java - 指定输出目录+包名+包模块名字
// mapper.xml 的位置：resources
public class CodeGenerator{
    public static void main(String[] args) {
        FastAutoGenerator.create(
                "jdbc:mysql://localhost:3306/vbenadmin?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC"
                        , "root"
                        , "root")
                .globalConfig(builder -> {
                    builder.author("maihehe") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(System.getProperty("user.dir") +"/mybatis-plus-generator/src/main/java/"); // 指定输出目录  <=
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com.vbenadmin.codegenerator") // 设置生成包名（文件路径）  <=
//                                .moduleName("layers") // 设置包模块名（文件路径）  <=
                                .pathInfo(Collections.singletonMap(OutputFile.xml,
                                System.getProperty("user.dir") + "/mybatis-plus-generator/src/main/resources/mapper")) // 设置mapperXml生成路径  <=
                                .entity("entity")
                                .mapper("mapper")
                                .service("service")
                                .serviceImpl("service.impl")
                                .xml("mapper.xml")
                )
                .strategyConfig(builder ->
                                builder.addInclude("sys_user_department") // 设置需要生成的表名 "ms_article" "ms_tag"
                                        .addTablePrefix("sys_") // 设置过滤表前缀
                                        .entityBuilder().enableLombok()
//                                .entityBuilder().enableLombok().enableFileOverride() // 开启文件覆盖 ，开启 Lombok
//                                .mapperBuilder().enableFileOverride() // 开启文件覆盖
//                                .serviceBuilder().enableFileOverride() // 开启文件覆盖
//                                .controllerBuilder().enableFileOverride() // 开启文件覆盖
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
