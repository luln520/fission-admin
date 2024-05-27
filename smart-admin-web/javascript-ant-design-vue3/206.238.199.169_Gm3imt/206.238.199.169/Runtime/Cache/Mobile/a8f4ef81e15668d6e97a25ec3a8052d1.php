<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
	<link rel="stylesheet" type="text/css" href="/Public/Static/css/base2.css" />
	<link rel="stylesheet" type="text/css" href="/Public/Static/css/nologed.css" />
	<link rel="stylesheet" href="/Public/Static/Icoinfont/iconfont.css">
	<script src="/Public/Static/Icoinfont/iconfont.js"></script>
	<title><?php echo ($webname); ?></title>
	<style>
	    ::-webkit-input-placeholder {color: #b5b5b5;font-size: 12px;}
	    ::-moz-placeholder {color: #b5b5b5;font-size: 12px;}
	    input:focus{background:#f5f5f5;outline: 1px solid #f5f5f5;}
	    a:hover,a:link,a:visited,a:active{color:#000000;text-decoration:none;}
	    .no_header{position: fixed;z-index: 9999;padding:0px 10px;top:0px;line-height: 50px;height: 60px;}
	    .contentbox{width:100%;height:600px;margin-top:65px;}
	    .contentbox_top{width:100%;height:50px;line-height:50px;text-align:left;padding:5px 15px;}
        .listbox{width:100%;height:100px;background:#f7f9fc;border-bottom:1px solid #f7f9fc;padding:5px 15px;}
        .listbox_title{width:100%;height:30px;line-height:30px;}
        .listbox_title_l{width:50%;height:30px;line-height:40px;float:left;text-align:left;}
        .listbox_title_r{width:50%;height:30px;line-height:40px;float:right;text-align:right;}
        html, body {
            background-color: #fff;
        }
        .no_title {
            width: 100%;
            height: 60px;
            line-height: 60px;
            position: absolute;
            text-align: center;
        }
        .goback-box {
            line-height: 50px;
            margin-left: 20px;
        }
	</style>
</head>
<body>
	<div class="container-fluid " style="padding:0px;width:100vw;">
        <div class="no_header">
            <div class="fl bhalf allhg txtl" style="line-height:50px;margin-left: 20px;">

            </div>
            <div class="no_title">
                <div class="goback-box fl">
                    <i class="bi bi-arrow-left fcc fw" onclick="goback()" style="font-size: 26px;"></i>
                </div>
                <span class="title_txt fe6im"><?php echo L('充币记录');?></span>
            </div>
		</div>
		<div class="contentbox">
		    <div style="width:100%;min-height:65vh;background:#fff;margin-top:5px;">
		        
		        <div style="width:100%;height:40px;line-height:40px;border-bottom:1px solid #f5f5f5;">
		            <div style="width:50%;height:40px;line-height:40px;text-align:center;float:left;">
		                <span><?php echo L('币种');?></span>
		            </div>
		            <div style="width:50%;height:40px;line-height:40px;text-align:center;float:right;">
		                <span><?php echo L('时间');?></span>
		            </div>
		        </div>
		        <?php if(empty($mlist)): ?><div class="listbox" style="border:none;">
		            <div style="width:100%;height:100px;line-height:100px;text-align:center;">
		                <span class="fzm fcc"><?php echo L('暂时没有充值记录');?></span>
		            </div>
		        </div>    
		        <?php else: ?>
		        <?php if(is_array($mlist)): foreach($mlist as $key=>$vo): ?><a href="<?php echo U('User/czinfo');?>?oid=<?php echo ($vo["id"]); ?>">
		        <div class="listbox">
                    <div class="listbox_title">
                        <div class="listbox_title_l">
                            <span class="fcc" style="font-size:18px;font-weight:500;"><?php echo ($vo["coin"]); ?></span>
                        </div>
                        <div class="listbox_title_r">
                            <?php if($vo['status']==1){?>
                            <span class="fcc" style="font-size:14px;"><?php echo L('确认中');?></span>
                            <?php }elseif($vo['status']==2){?>
                            <span class="fcc" style="font-size:14px;"><?php echo L('完成');?></span>
                            <?php }elseif($vo['status']==3){?>
                            <span class="fcc" style="font-size:14px;"><?php echo L('原路返回');?></span>
                            <?php }?>
                        </div>
                    </div>
                    <div style="width:100%;height:60px;">
                        <div style="width:33.33%;height:60px;float:left;">
                            <div style="width:100%;height:30px;line-height:40px;">
                                <span style="color:#cbcbcb;"><?php echo L('数量');?></span>
                            </div>
                            <div style="width:100%;height:30px;line-height:30px;">
                                <span class="fch"><?php echo ($vo["num"]); ?></span>
                            </div>
                        </div>
                        <div style="width:33.33%;height:60px;float:left;">
                            <div style="width:100%;height:30px;line-height:40px;">
                                <span style="color:#cbcbcb;"><?php echo L('手续费');?></span>
                            </div>
                            <div style="width:100%;height:30px;line-height:30px;">
                                <span class="fch">0.000000</span>
                            </div>
                        </div>
                        <div style="width:33.33%;height:60px;float:left;">
                            <div style="width:100%;height:30px;line-height:40px;text-align:right;">
                                <span style="color:#cbcbcb;"><?php echo L('时间');?></span>
                            </div>
                            <div style="width:100%;height:30px;line-height:30px;text-align:right;">
                                <span class="fch"><?php echo date("m-d H:i",strtotime($vo['addtime']))?></span>
                            </div>
                        </div>
                    </div>
                </div>
                </a><?php endforeach; endif; endif; ?>
                

		        
		    </div>
		    
		    

		    <div style="width:100%;height:80px;"></div>
   
		</div>

	</div>	
</body>
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/Public/Static/js/layer/layer.js" ></script>

<script type="text/javascript">
    function goback(){
        window.history.go(-1);
    }
</script>
</html>