package com.atguigu.ossservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.baseservice.handler.GuliException;
import com.atguigu.ossservice.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.atguigu.ossservice.service.FileService;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFileOSS(MultipartFile file) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        //获取指定目录
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        //指定文件名
        String fileName = file.getOriginalFilename();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();

            // 补充文件名,保证文件名的唯一性,给原有文件名+UUID
            //优化文件名（11112222+01.jpg）
            fileName = UUID.randomUUID().toString() + fileName;

            // 补充文件名,使其在 OSS 中以日期的的目录存在
            //优化文件存储路径（/2021/03/09/uuid+01.jpg）
            String path = new DateTime().toString("yyyy/MM/dd");
            fileName = path + "/" + fileName;

            // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //文件上传成功的url
            //https://guil-edu.oss-cn-beijing.aliyuncs.com/aliyunup.jpg
            String url = "https://" + endpoint + "/" + fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20001, "上传失败");
        }
    }
}

