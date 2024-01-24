package net.lab1024.sa.admin.module.system.TwPC.controller;

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
 * 文件服务
 *
 */
@RestController
@Api(tags = {AdminSwaggerTagConst.PC.PC_FILE})
@RequestMapping("/api/pc")
public class PcFileController extends SupportBaseController {

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
}
