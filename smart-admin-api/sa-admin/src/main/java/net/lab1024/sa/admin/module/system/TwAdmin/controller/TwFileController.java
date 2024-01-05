package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import cn.hutool.extra.servlet.ServletUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.controller.SupportBaseController;
import net.lab1024.sa.common.common.domain.RequestUser;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.SmartRequestUtil;
import net.lab1024.sa.common.constant.SwaggerTagConst;
import net.lab1024.sa.common.module.support.file.constant.FileFolderTypeEnum;
import net.lab1024.sa.common.module.support.file.domain.form.FileUrlUploadForm;
import net.lab1024.sa.common.module.support.file.domain.vo.FileUploadVO;
import net.lab1024.sa.common.module.support.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 公用文件上传接口
 */
@RestController
@RequestMapping("/api/admin/file")
@Api(tags = {AdminSwaggerTagConst.System.TW_FILE})
public class TwFileController extends SupportBaseController {
    /**
     * 文件上传接口   file（文件流） baseFileName（文件夹名称） 需要开放静态文件访问路径
     * 1.限制file类型为 图片 和 10M以内('jpg', 'gif', 'png', 'jpeg')
     * 2.只需要把文件 写入对应 baseFileName （文件夹名称）就行 基础存放路径自定义（开放为静态访问路径就行）
     * 3.返回文件相对 路径   baseFileName/xxx.png(jpg)
     */
    @Autowired
    private FileService fileService;


    @ApiOperation(value = "文件上传", notes = FileFolderTypeEnum.INFO)
    @PostMapping("/file/upload")
    @NoNeedLogin
    public ResponseDTO<FileUploadVO> upload(@RequestParam MultipartFile file,
                                            @RequestParam Integer folder) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        return fileService.fileUpload(file, folder, requestUser);
    }

    @ApiOperation(value = "文件上传，通过url上传", notes = FileFolderTypeEnum.INFO)
    @PostMapping("/file/upload/url")
    @NoNeedLogin
    public ResponseDTO<FileUploadVO> uploadByUrl(@RequestBody @Valid FileUrlUploadForm uploadForm) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        return fileService.fileUpload(uploadForm,requestUser);
    }

    @ApiOperation("获取文件URL：根据fileKey ")
    @GetMapping("/file/getFileUrl")
    @NoNeedLogin
    public ResponseDTO<String> getUrl(@RequestParam String fileKey) {
        return fileService.getFileUrl(fileKey);
    }

    @ApiOperation(value = "下载文件流（根据fileKey）")
    @GetMapping("/file/downLoad")
    @NoNeedLogin
    public ResponseEntity<Object> downLoad(@RequestParam String fileKey, HttpServletRequest request) {
        String userAgent = ServletUtil.getHeaderIgnoreCase(request, RequestHeaderConst.USER_AGENT);
        return fileService.downloadByFileKey(fileKey, userAgent);
    }

    /**
     * 有file文件时
     * @param file 图片file
     */
    @PostMapping("/upload")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO editMovieInfo(@RequestParam("file")MultipartFile file) {
          return  fileService.editMovieInfo(file);

    }
}

