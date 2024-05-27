<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="/Public/Static/css/base.css" />
    <link rel="stylesheet" type="text/css" href="/Public/Static/css/nologed.css" />
    <title><?php echo ($webname); ?></title>
    <style>
        ::-webkit-input-placeholder { /* WebKit browsers */
            color: #b5b5b5;
            font-size: 18px;
        }

        ::-moz-placeholder { /* Mozilla Firefox 19+ */
            color: #b5b5b5;
            font-size: 18px;
        }
        a:hover,a:link,a:visited,a:active{color:#FCD535;text-decoration:none;}
        .lbox{width:100%;height:60px;margin-top: 20px;border-radius: 10px;}
        .lbox_l{width:70%;height:60px;line-height:60px;text-align:left;float:left;background: #1b1d2a;border-radius: 10px 0 0 10px;}
        .lbox_r{width:30%;height:60px;line-height:60px;text-align:right;float:left;background: #1b1d2a;padding-right: 10px;border-radius: 0 10px 10px 0;}

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
            position: absolute;
        }

        .no_inbox {
            margin-top: 10px;
        }

    </style>
</head>
<body>
<div class="container-fluid ctbox">
    <div class="no_header">
        <div class="fl bhalf allhg txtl" style="line-height:50px;margin-left: 20px;">

        </div>
        <div class="no_title">
            <div class="goback-box fl">
                <i class="bi bi-arrow-left fcc fw" onclick="goback()" style="font-size: 26px;"></i>
            </div>
            <span class="title_txt fe6im"><?php echo L('修改密码');?></span>
        </div>
    </div>

    <div class="no_content">

        <div class="no_inbox">

            <a href="<?php echo U('Index/respwd');?>">
                <div class="lbox">
                    <div class="lbox_l">
                        <span class="fcc fe6im fzmmm" style="font-size:14px;">&nbsp;&nbsp;<?php echo L('登录'); echo L('密码');?></span>
                    </div>
                    <div class="lbox_r">
                        <i class="bi bi-chevron-right  fcc fe6im fw" style="font-size: 16px;"></i>
                    </div>
                </div>
            </a>
            <a href="<?php echo U('Index/withdrawpwd');?>">
                <div class="lbox">
                    <div class="lbox_l">
                        <span class="fcc fzmmm fe6im" style="font-size:14px;">&nbsp;&nbsp;<?php echo L('提现密码');?></span>
                    </div>
                    <div class="lbox_r">
                        <?php if($userInfo["paypassword"] != ''): ?><span class="f12  rzgreen"><?php echo L('已设置');?></span>
                        <?php else: ?>
                            <span class="f12  rzred"><?php echo L('未设置');?></span><?php endif; ?>
                        <i class="bi bi-chevron-right fcc fw fe6im" style="font-size: 16px;"></i>
                    </div>
                </div>
            </a>
        </div>

    </div>

</div>
</body>

<body>

<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/Public/Static/js/layer/layer.js" ></script>
<script type="text/javascript">
    function goback(){
        window.history.go(-1);
    }
</script>
</html>