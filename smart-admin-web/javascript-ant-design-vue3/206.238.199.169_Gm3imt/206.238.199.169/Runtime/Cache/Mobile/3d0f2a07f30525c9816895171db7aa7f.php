<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css"
		  integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

	<link rel="stylesheet" href="/Public/Admin/css/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
	<link rel="stylesheet" type="text/css" href="/Public/Static/css/base2.css" />
	<link rel="stylesheet" type="text/css" href="/Public/Static/css/nologed.css" />
	<link rel="stylesheet" href="/Public/Static/Icoinfont/iconfont.css">
	<script src="/Public/Static/Icoinfont/iconfont.js"></script>
	<title><?php echo ($webname); ?></title>
	<style>
	    ::-webkit-input-placeholder {color: #b5b5b5;font-size: 12px;}
	    ::-moz-placeholder {color: #b5b5b5;font-size: 12px;}
	    a:hover,a:link,a:visited,a:active{color:#000000;text-decoration:none;}
	    .no_header{position: fixed;z-index: 9999;padding:0px 10px;top:0px;line-height: 50px;background:#121420;}
	    .contentbox{width:100%;height:600px;margin-top:45px;padding:5px 15px;}
	    .contentbox_top{width:100%;height:50px;line-height:50px;text-align:center;z-index: -1}
	    .contentbox_line{width:100%;background:#121420;border-radius:10px;padding:0px 10px;}
	    .line_2{width:100%;padding:10px;}
	    .imgbox{width:100%;padding:10px;background:#121420;margin-top:15px;border-radius:10px;}
	    .imgbox_1{width:100%;height:40px;line-height:40px;text-align:left;}
	    .imgbox_2{width:100%;height:35px;line-height:35px;text-align:left;}
	    .imgbox_3{width:100px;height:35px;line-height:35px;text-align:center;border:1px solid #FCD535;border-radius:5px;}
	    .imgbox_4{width:100%;margin-top:15px;border-top:1px solid #f5f5f5;padding: 10px 0px 20px 0px;}
	    .imgbox_5{width:100%;height:40px;line-height:40px;text-align:left;}
	    .imgbox_6{width:100%;height:120px;margin-top:10px;text-align:center;background: #1b1d2a;border-radius: 10px;}
	    .imgbox_7{width:100%;height:30px;line-height:30px;text-align:center;margin-top:10px;}
	    .imgbox_8{width:100%;height:30px;line-height:30px;text-align:center;}
	    .imgbox_9{width:100px;height:30px;line-height:30px;text-align:center;border-radius:5px;border:1px solid #FCD535;margin:auto;}
	    .imgbox_10{width:100%;height:40px;line-height:40px;text-align:center;border-radius:5px;margin:auto;background: #3db485;color: #fff;}
	    .textbox{width:100%;background:#fff;margin-top:10px;border-radius:10px;padding:15px;}
	    
	    .zz_inputbox{width:100%;height:40px;line-height:40px;background:#1b1d2a;border-radius:10px;padding:0px 15px;}

	    .zz_input{
			height:30px;
			line-height:30px;
			margin-top:5px;
			border:#1b1d2a;
			background:#1b1d2a;
			width:100%;
			color: #e6e6e6;
			outline:none;
		}
	    .layui-upload-file{display: none!important;opacity: .01;}

		input:focus {
			border: none;
			outline: none;
		}

		.inputbox-select{
			width: 100%;
			height: 35px;
			position: relative;
			background: transparent;
			color: #e6e6e6;
			margin-top: 2px;

		}

		select{
			border: none;
			outline: none;
			width: 100%;
			height: 40px;
			line-height: 40px;
			appearance: none;
			-webkit-appearance: none;
			-moz-appearance: none;
		}

		.inputbox-select:after{
			content: "";
			width: 14px;
			height: 8px;
			position: absolute;
			right: 20px;
			top: 45%;
			pointer-events: none;
			outline: none;
		}

	</style>
</head>
<body>
	<div class="container-fluid " style="padding:0px;width:100vw;">
        <div class="no_header">
			<div class="fl allhg txtl">
				<i class="bi bi-arrow-left fcc fw" onclick="goback()" style="font-size: 24px;"></i>
			</div>
			<div class="contentbox_top">
				<span class="fe6im" style="font-size:18px;font-weight:500;"><?php echo L('实名认证');?></span>
			</div>

			<div class="fr allhg txtr" style="line-height:50px;width:10%;">
			</div>
	
		</div>
		<div class="contentbox">
		    <div class="contentbox_line">
		        <div class="line_2 tcc">
		            <span class="fe6im f12"><?php echo L('完成实名认证后能够获得相应的权益');?></span>
		        </div>
		    </div>
		    <div class="imgbox">
		        <div class="imgbox_4"  style="border:none;margin:0px;">

<!--		            <div class="imgbox_5">-->
<!--		                <span class="fzmmm fe6im f12"><?php echo L('手机号码');?></span>-->
<!--		            </div>-->
<!--		            <div class="zz_inputbox">-->
<!--		                <input type="text" id="phone" class="zz_input" value="<?php echo ($info["phone"]); ?>"   placeholder="<?php echo L('请输入手机号码');?>" />-->
<!--		            </div>-->

<!--					<div class="imgbox_5">-->
<!--						<span class="fzmmm fe6im f12"><?php echo L('国家');?></span>-->
<!--					</div>-->
<!--					<div class="zz_inputbox">-->
<!--						<input type="text" id="country" class="zz_input" value="<?php echo ($info["phone"]); ?>"   placeholder="<?php echo L('请输入手机号码');?>" />-->
<!--					</div>-->

<!--					<div class="imgbox_5">-->
<!--						<span class="fzmmm fe6im f12"><?php echo L('地区');?></span>-->
<!--					</div>-->
<!--					<div class="zz_inputbox">-->
<!--						<input type="text" id="area" class="zz_input" value="<?php echo ($info["area"]); ?>"   placeholder="<?php echo L('请输入手机号码');?>" />-->
<!--					</div>-->

			<!--	<div class="imgbox_5">
						<span class="fzmmm fe6im f12"><?php echo L('手机号码');?></span>
					</div>
					<div class="zz_inputbox">
						<input type="text"  id="phone" class="zz_input" value="<?php echo ($info["phone"]); ?>"   placeholder="<?php echo L('请输入手机号码');?>" <?php if($info["rzstatus"] == 2 || $info["rzstatus"] == 1): ?>disabled<?php endif; ?> />
					</div>
-->
					<div class="imgbox_5">
						<span class="fzmmm fe6im f12"><?php echo L('真实姓名');?></span>
					</div>
					<div class="zz_inputbox">
						<input type="text"  id="real_name" class="zz_input" value="<?php echo ($info["real_name"]); ?>"   placeholder="<?php echo L('请输入真实姓名');?>" <?php if($info["rzstatus"] == 2 || $info["rzstatus"] == 1): ?>disabled<?php endif; ?> />
					</div>

					<div class="imgbox_5">
						<span class="fzmmm fe6im f12"><?php echo L('认证类型');?></span>
					</div>
					<div class="zz_inputbox " style="position: relative;">
						<div class="i-svg-x" style="position: absolute;top: 0;right: 5%;">
							<?php echo '<?'; ?>
xml version="1.0" encoding="UTF-8"?>
							<svg width="24" height="24" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M37 18L25 30L13 18" stroke="#e6e6e6" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
							</svg>
						</div>
						<select <?php if($info["rzstatus"] == 2 || $info["rzstatus"] == 1): ?>disabled<?php endif; ?>  class="inputbox-select" id="rztype" name="rztype" aria-label="Default select example">
							<option value="1" <?php if($info["rztype"] == 1): ?>selected<?php endif; ?>  ><?php echo L('护照');?></option>
							<option value="2" <?php if($info["rztype"] == 2): ?>selected<?php endif; ?>  ><?php echo L('驾驶证');?></option>
							<option value="3" <?php if($info["rztype"] == 3): ?>selected<?php endif; ?>  ><?php echo L('SSN');?></option>
							<option value="4" <?php if($info["rztype"] == 4): ?>selected<?php endif; ?>  ><?php echo L('身份ID');?></option>
						</select>
					</div>
		            <div class="imgbox_5">
		                <span class="fzmmm fe6im f12"><?php echo L('上传证件正面');?></span>
		            </div>
		            <div class="imgbox_6">
		                <?php if($info['cardzm'] != ''){ ?>
		                <img src="/Public/Static/payimgs/<?php echo $info['cardzm'];?>" <?php if($info["rzstatus"] == 0 || $info["rzstatus"] == 4): ?>id="test1"<?php endif; ?> style="height:100px;" />
		                <input type="hidden" id="cardzm" value="<?php echo $info['cardzm'];?>" <?php if($info["rzstatus"] == 2 || $info["rzstatus"] == 1): ?>disabled<?php endif; ?> />
		                <?php }else{?>
		                <img src="/Public/Static/img/upimg.png" <?php if($info["rzstatus"] == 0 || $info["rzstatus"] == 4): ?>id="test1"<?php endif; ?>  style="height:100px;" />
		                <input type="hidden" id="cardzm" value=""  <?php if($info["rzstatus"] == 2 || $info["rzstatus"] == 1): ?>disabled<?php endif; ?> />
		                <?php }?>

		            </div>
		            
		             <div class="imgbox_5">
		                <span class="fzmmm fe6im f12"><?php echo L('上传证件反面');?></span>
		            </div>
		            
		            <div class="imgbox_6">
		                <?php if($info['cardfm'] != ''){ ?>
		                <img src="/Public/Static/payimgs/<?php echo $info['cardfm'];?>" <?php if($info["rzstatus"] == 0 || $info["rzstatus"] == 4): ?>id="test2"<?php endif; ?>  style="height:100px;" />
		                <input type="hidden" id="cardfm" value="<?php echo $info['cardfm'];?>" <?php if($info["rzstatus"] == 2 || $info["rzstatus"] == 1): ?>disabled<?php endif; ?> />
		                <?php }else{?>
		                <img src="/Public/Static/img/upimg.png" <?php if($info["rzstatus"] == 0 || $info["rzstatus"] == 4): ?>id="test2"<?php endif; ?>  style="height:100px;" <?php if($info["rzstatus"] == 2 || $info["rzstatus"] == 1): ?>disabled<?php endif; ?> />
		                <input type="hidden" id="cardfm" value="" />
		                <?php }?>
		                
		            </div>
		            <input type="hidden" id="flag" value="1" />
		            
		            <?php if($info["rzstatus"] == 2): ?><div class="imgbox_8">
		                <div class="imgbox_10">
		                    <span class="fzmm "><?php echo L('认证审核成功');?></span>
		                </div>
		            </div>
		            <?php else: ?>
						<?php if($info["rzstatus"] == 1): ?><div class="imgbox_10">
								<span class="fzmm "><?php echo L('审核中');?></span>
							</div>
						<?php else: ?>
							<div class="imgbox_8">
								<div class="imgbox_10" id="sumbtn">
									<span class="fzmm "><?php echo L('提交');?></span>
								</div>
							</div><?php endif; endif; ?>
		            
		        </div>
		    </div>

		    <div style="width:100%;height:80px;"></div>
   
		</div>

	</div>	
</body>
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/Public/Static/js/layer/layer.js" ></script>
<script type="text/javascript" src="/Public/layui/layui.js"></script>
<script type="text/javascript">
    $("#sumbtn").click(function(){
        var flag = $("#flag").val();
        if(flag == 2){
            return false;
        }
        var phone = $("#phone").val();
        // var area = $("#area").val();
        var area = 'area';
        var real_name = $("#real_name").val();
        var rztype = $("#rztype").val();
        var cardzm = $("#cardzm").val();
        var cardfm = $("#cardfm").val();
       if(phone == ''){
            layer.msg("<?php echo L('请填写手机号码');?>");return false;
        }
        if(area == ''){
            layer.msg("<?php echo L('请填写地区');?>");return false;
        }

		if(real_name == ''){
			layer.msg("<?php echo L('请填写真实姓名');?>");return false;
		}

		if(rztype == ''){
			layer.msg("<?php echo L('请选择认证类型');?>");return false;
		}

        if(cardzm == null || cardzm == ''){
            layer.msg("<?php echo L('请上传上传身份证正面');?>");return false;
        }

        if(cardfm == ''){
            layer.msg("<?php echo L('请上传上传身份证背面');?>");return false;
        }

        $("#flag").val(2)
        $.post("<?php echo U('User/upauthrz');?>",
        {'phone' : phone,'real_name' : real_name,'cardzm':cardzm,'cardfm':cardfm,'area':area,'rztype':rztype},
        function(data){
            if(data.code == 1){
                layer.msg(data.info);
                setTimeout(function(args){
                    window.location.reload();
                },2000);
            }else{
                layer.msg(data.info);return false;
            }
        }
        );
        
    });
</script>

<script type="text/javascript">
      layui.use('upload', function(){
        var upload = layui.upload;
        var uploadInst = upload.render({
            elem: '#test1' //绑定元素
            ,url: '<?php echo U("User/recharge_img");?>' //上传接口
            ,done: function(res){
                console.log(res);
                if(res.code == 0){
                    $('#cardzm').val(res.data.img);
                    $("#test1").attr('src',res.data.src);
                }
            }
            ,error: function(){
                layer.msg("<?php echo L('上传失败');?>");
            }
        });
    });
</script>
<script type="text/javascript">
      layui.use('upload', function(){
        var upload = layui.upload;
        var uploadInst = upload.render({
            elem: '#test2' //绑定元素
            ,url: '<?php echo U("User/recharge_img");?>' //上传接口
            ,done: function(res){
                console.log(res);
                if(res.code == 0){
                    $('#cardfm').val(res.data.img);
                    $("#test2").attr('src',res.data.src);
                }
            }
            ,error: function(){
                layer.msg("<?php echo L('上传失败');?>");
            }
        });
    });
</script>

<script type="text/javascript">
    function goback(){
        window.history.go(-1);
    }
</script>
</html>