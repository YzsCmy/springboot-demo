package com.unionman.springbootdemo.fastdfs;


import com.github.tobato.fastdfs.domain.conn.ConnectionManager;
import com.github.tobato.fastdfs.domain.fdfs.StorageNode;
import com.github.tobato.fastdfs.domain.fdfs.StorageNodeInfo;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.StorageDeleteFileCommand;
import com.github.tobato.fastdfs.domain.proto.storage.StorageSetMetadataCommand;
import com.github.tobato.fastdfs.domain.proto.storage.StorageUploadFileCommand;
import com.github.tobato.fastdfs.domain.proto.storage.enums.StorageMetadataSetType;
import com.github.tobato.fastdfs.domain.upload.FastFile;
import com.github.tobato.fastdfs.service.TrackerClient;
import com.unionman.springbootdemo.config.FdfsGroupsConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


@Component
@DependsOn("fdfsGroupsConfig")
@Order
@Slf4j
public class FdfsClient {

    @Autowired
    protected TrackerClient trackerClient;

    @Autowired
    protected ConnectionManager connectionManager;

    @Autowired
    private FdfsGroupsConfig fdfsGroupsConfig;

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    public String uploadFile(File file) {
        FastFile fastFile;
        try {
            fastFile = new FastFile.Builder()
                    .withFile(new FileInputStream(file), file.length(), "jpg")
                    .build();
        } catch (FileNotFoundException e) {
            log.error("[57 Row] FileNotFoundException {}", e.getMessage());
            return null;
        }
        Validate.notNull(fastFile.getInputStream(), "上传文件流不能为空");
        Validate.notBlank(fastFile.getFileExtName(), "文件扩展名不能为空");
        // 获取存储节点
        StorageNode client;
        if (null == fastFile.getGroupName()) {
            client = trackerClient.getStoreStorage();
        } else {
            client = trackerClient.getStoreStorage(fastFile.getGroupName());
        }
        client.setIp(fdfsGroupsConfig.getGroupMap().get(client.getGroupName()));
        //上传文件
        StorageUploadFileCommand command = new StorageUploadFileCommand(client.getStoreIndex(), fastFile.getInputStream(),
                fastFile.getFileExtName(), fastFile.getFileSize()
                , false);
        StorePath storePath = connectionManager.executeFdfsCmd(client.getInetSocketAddress(), command);
        //上传metadata
        if (null != fastFile.getMetaDataSet() && !fastFile.getMetaDataSet().isEmpty()) {
            StorageSetMetadataCommand setMdCommand = new StorageSetMetadataCommand(storePath.getGroup(), storePath.getPath(),
                    fastFile.getMetaDataSet(), StorageMetadataSetType.STORAGE_SET_METADATA_FLAG_OVERWRITE);
            connectionManager.executeFdfsCmd(client.getInetSocketAddress(), setMdCommand);
        }
        return storePath.getFullPath();
    }



    public String uploadFile(InputStream inputStream,Long length,String extName) {
        FastFile fastFile;
        try {
            fastFile = new FastFile.Builder()
                    .withFile(inputStream, length, extName)
                    .build();
        }catch (Exception e) {
            log.error("[57 Row] FileNotFoundException {}", e.getMessage());
            return null;
        }
        Validate.notNull(fastFile.getInputStream(), "上传文件流不能为空");
        Validate.notBlank(fastFile.getFileExtName(), "文件扩展名不能为空");
        // 获取存储节点
        StorageNode client;
        if (null == fastFile.getGroupName()) {
            client = trackerClient.getStoreStorage();
        } else {
            client = trackerClient.getStoreStorage(fastFile.getGroupName());
        }
        client.setIp(fdfsGroupsConfig.getGroupMap().get(client.getGroupName()));
        //上传文件
        StorageUploadFileCommand command = new StorageUploadFileCommand(client.getStoreIndex(), fastFile.getInputStream(),
                fastFile.getFileExtName(), fastFile.getFileSize()
                , false);
        StorePath storePath = connectionManager.executeFdfsCmd(client.getInetSocketAddress(), command);
        //上传metadata
        if (null != fastFile.getMetaDataSet() && !fastFile.getMetaDataSet().isEmpty()) {
            StorageSetMetadataCommand setMdCommand = new StorageSetMetadataCommand(storePath.getGroup(), storePath.getPath(),
                    fastFile.getMetaDataSet(), StorageMetadataSetType.STORAGE_SET_METADATA_FLAG_OVERWRITE);
            connectionManager.executeFdfsCmd(client.getInetSocketAddress(), setMdCommand);
        }
        return storePath.getFullPath();
    }




    /**
     * 删除图片
     *
     * @return
     */
    public Boolean deleteFile(String imageUrl) {
        try {
            StorePath storePath = StorePath.parseFromUrl(imageUrl);
            StorageNodeInfo client = trackerClient.getUpdateStorage(storePath.getGroup(), storePath.getPath());
            StorageDeleteFileCommand command = new StorageDeleteFileCommand(storePath.getGroup(), storePath.getPath());
            client.setIp(fdfsGroupsConfig.getGroupMap().get(client.getGroupName()));
            connectionManager.executeFdfsCmd(client.getInetSocketAddress(), command);
        } catch (Exception e) {
            log.error("[97 Row] Exception {}", e.getMessage());
            return false;
        }
        return true;
    }

}
