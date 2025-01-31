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
		input:focus{background:#F5F5F5;outline: 1px solid #F5F5F5;}
		a:hover,a:link,a:visited,a:active{color:#FCD535;text-decoration:none;}
		.lbox{
			width:100%;
			height:60px;
			padding: 0 3%;
			background: #1b1d2a;

		}
		.lbox_f_br {
			border-radius: 20px 20px 0 0 ;
		}



		.lbox_l{width:70%;height:60px;line-height:60px;text-align:left;float:left;}
		.lbox_r{width:30%;height:60px;line-height:60px;text-align:right;float:left;}
		.userbox{
			width: 100%;
			background: url(/Public/Static/img/index.png) no-repeat;
			background-size: 100% 167px;
			padding: 25px 3%;
			color: #fff;
			height: 167px;
		}

		.alltn {
			width: 100%;
			height: 40px;
			line-height: 40px;
			text-align: center;
			border-radius: 5px;
			background: #0052fe;
			color: #fff;
		}

		.userbox_left {
			float: left;
			width: 30%;
			height: 100px;
			text-align: left;
			line-height: 100px;
		}
		.userbox_right {
			float: right;
			width: 70%;
			height: 100px;
		}
		#config_app_log {
			border-radius: 50%;
			width: 55%;
		}

		.no_content {
			height: 60%;
			padding: 0;
		}

		.lbox-j {
			margin-bottom: 10px;
		}
		body {
			background: #008477;
			background-color: #008477;
		}



		.f16{font-size:16px;}
		.f12{font-size:12px;}
	</style>
  </head>
  <body>
	<div class="container-fluid"  style="padding:0px;width:100vw;height:100vh;background: #121420">
		<div class="no_header"  style="position: fixed;z-index: 9999;background: transparent;padding:0px 10px;top:0px;">
			<div class="fl bhalf allhg txtl" style="line-height:50px;">
				<i class="bi bi-arrow-left fe6im fw" onclick="goindex()" style="font-size: 26px;"></i>
			</div>
			<a href="<?php echo U('user/online');?>">
			<div class="fr bhalf allhg txtr" style="line-height:50px;">
				<i class="bi bi-headset fe6im fw"   style="font-size: 26px;"></i>
			</div>
			</a>
		</div>

		<div class="no_content" style="width:100%;margin:auto;">

			<div class="userbox">
				<div class="userbox_left" >
					<img src="<?php echo ($waplogo); ?>" id="config_app_log" >
				</div>
				<div class="userbox_right">
					<p style="font-size:20px;margin-bottom:5px;margin-top:20px;" class=""><?php echo ($info["username"]); ?></p>
					<input type="hidden" id="invit" value="<?php echo ($info["invit"]); ?>" />
					<span class="fzm "><?php echo L('邀请码');?>：<?php echo ($info["invit"]); ?> &nbsp;&nbsp;</span>
					<i class="bi bi-back " onclick="copyUrl()"></i>
				</div>

			</div>

			<div class="no_inbox lbox_f_br">

				<a href="<?php echo U('User/authrz');?>">
					<div class="lbox lbox_f_br lbox-j">
						<div class="lbox_l">
							<svg t="1654074073313" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="22677" width="22" ><path d="M511.953455 1002.146909c-142.987636 0-408.901818-218.763636-408.901818-425.634909L103.051636 164.421818l40.657455-0.674909c0.861091 0 91.624727-1.931636 185.274182-39.936 96.046545-39.028364 157.998545-83.828364 158.580364-84.247273l24.273455-17.687273 24.482909 17.687273c0.581818 0.442182 62.533818 45.218909 158.580364 84.247273 93.649455 38.004364 184.413091 39.936 185.367273 39.936l40.471273 0.674909 0.186182 412.090182C920.948364 783.36 655.034182 1002.146909 511.953455 1002.146909L511.953455 1002.146909zM185.623273 243.409455l0 333.079273c0 159.953455 231.633455 343.063273 326.330182 343.063273 94.72 0 326.330182-183.109818 326.330182-343.063273L838.283636 243.409455c-40.471273-4.375273-106.170182-15.429818-174.405818-43.124364-69.934545-28.439273-123.042909-59.345455-151.947636-77.754182-28.811636 18.408727-81.989818 49.314909-151.854545 77.754182C291.793455 228.002909 226.071273 239.034182 185.623273 243.409455L185.623273 243.409455zM490.077091 731.345455l-173.614545-147.898182 53.387636-62.813091 111.383273 94.813091 211.386182-243.525818 62.417455 54.155636L490.077091 731.345455 490.077091 731.345455zM490.077091 731.345455" p-id="22678" fill="#707a8a"></path></svg>
							<span class="fe6im f16">&nbsp;&nbsp;<?php echo L('实名认证');?></span>
						</div>
						<div class="lbox_r">
							<?php if($rzstatus == 2): ?><span class="f12  rzgreen"><?php echo L('已认证');?></span>
							<?php elseif($rzstatus == 1): ?>
								<span class="f12  fe6im"><?php echo L('审核中');?></span>
							<?php else: ?>
								<span class="f12  rzred"><?php echo L('未认证');?></span><?php endif; ?>
							<i class="bi bi-chevron-right fcc fw" style="font-size: 16px;"></i>
						</div>
					</div>
				</a>

				<a href="<?php echo U('Login/setlang');?>">
					<div class="lbox ">
						<div class="lbox_l">
							<i class="bi bi-globe fcc fzmmm" style="font-size:22px;"></i>
							<span class="fe6im f16">&nbsp;&nbsp;<?php echo L('语言');?></span>
						</div>
						<div class="lbox_r">
							<?php if(LANG_SET=='zh-cn'){?>
							<span class="fe6im fzmmm" style="font-size:12px;">中文简体&nbsp;&nbsp;</span>
							<?php }elseif(LANG_SET=='en-us'){?>
							<span class="fe6im fzmmm" style="font-size:12px;">English&nbsp;&nbsp;</span>
							<?php }elseif(LANG_SET=='fr-fr'){?>
							<span class="fe6im fzmmm" style="font-size:12px;">Français&nbsp;&nbsp;</span>
							<?php }elseif(LANG_SET=='de-de'){?>
							<span class="fe6im fzmmm" style="font-size:12px;">Deutsch&nbsp;&nbsp;</span>
							<?php }elseif(LANG_SET=='it-it'){?>
							<span class="fe6im fzmmm" style="font-size:12px;">Italiano&nbsp;&nbsp;</span>
							<?php }elseif(LANG_SET=='ja-jp'){?>
							<span class="fe6im fzmmm" style="font-size:12px;">日本語&nbsp;&nbsp;</span>
							<?php }?>



							<i class="bi bi-chevron-right fcc fw" style="font-size: 16px;"></i>
						</div>
					</div>
				</a>

				<a href="<?php echo U('User/addresslist');?>">
					<div class="lbox ">
						<div class="lbox_l">
							<i class="bi bi-wallet2 fcc fzmmm" style="font-size:22px;"></i>
							<span class="fe6im f16">&nbsp;&nbsp;<?php echo L('提币地址');?></span>
						</div>
						<div class="lbox_r">
							<i class="bi bi-chevron-right fcc fw" style="font-size: 16px;"></i>
						</div>
					</div>
				</a>

				<a href="<?php echo U('Index/spwd');?>">
					<div class="lbox lbox-j">
						<div class="lbox_l">
							<i class="bi bi-shield-check fcc fzmmm" style="font-size:22px;"></i>
							<span class="fe6im f16">&nbsp;&nbsp;<?php echo L('密码设置');?></span>
						</div>
						<div class="lbox_r">

							<i class="bi bi-chevron-right fcc fw" style="font-size: 16px;"></i>
						</div>
					</div>
				</a>

				<a href="<?php echo U('Login/lhelp');?>">
					<div class="lbox">
						<div class="lbox_l">
							<i class="bi bi-question-square fcc fzmmm" style="font-size:22px;"></i>
							<span class="fe6im f16">&nbsp;&nbsp;<?php echo L('帮助中心');?></span>
						</div>
						<div class="lbox_r">
							<i class="bi bi-chevron-right fcc fw" style="font-size: 16px;"></i>
						</div>
					</div>
				</a>

				<a href="<?php echo U('Index/notice');?>">
					<div class="lbox ">
						<div class="lbox_l">
							<i class="bi bi-bell fcc fzmmm" style="font-size:22px;"></i>
							<span class="fe6im f16">&nbsp;&nbsp;<?php echo L('通知');?></span>
						</div>
						<div class="lbox_r">
							<?php if($count >= 1): ?><span class="f12 fe6im"><?php echo L('新通知');?></span><?php endif; ?>
							<i class="bi bi-chevron-right fcc fw" style="font-size: 16px;"></i>
						</div>
					</div>
				</a>

				<a href="<?php echo U('Index/tgcode');?>">
					<div class="lbox">
						<div class="lbox_l">
							<svg t="1654074441198"  viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="29648" width="22" ><path d="M901.12 1024H122.88C57.344 1024 4.096 970.752 4.096 905.216v-389.12c0-43.008 22.528-79.872 55.296-100.352 32.768-18.432 71.68-18.432 106.496 2.048l311.296 186.368c20.48 12.288 47.104 12.288 67.584 0L860.16 415.744c36.864-18.432 77.824-16.384 108.544 4.096s49.152 55.296 49.152 98.304v389.12c2.048 63.488-51.2 116.736-116.736 116.736zM112.64 493.568c-2.048 0-4.096 0-6.144 2.048-6.144 4.096-10.24 12.288-10.24 22.528v389.12c0 14.336 12.288 26.624 26.624 26.624h778.24c14.336 0 26.624-12.288 26.624-26.624v-389.12c0-10.24-2.048-18.432-6.144-20.48-4.096-2.048-10.24-2.048-20.48 2.048l-309.248 186.368c-49.152 30.72-112.64 30.72-161.792 0L120.832 495.616c-4.096 0-6.144-2.048-8.192-2.048zM815.104 376.832c-24.576 0-45.056-20.48-45.056-45.056v-204.8c0-18.432-16.384-34.816-34.816-34.816H288.768c-18.432 0-34.816 16.384-34.816 34.816v204.8c0 24.576-20.48 45.056-45.056 45.056-24.576 0-45.056-20.48-45.056-45.056v-204.8C163.84 55.296 219.136 0 288.768 0h444.416C802.816 0 860.16 55.296 860.16 124.928v204.8c0 26.624-20.48 47.104-45.056 47.104z" fill="#707a8a" p-id="29649"></path><path d="M593.92 370.688h-165.888c-24.576 0-45.056-20.48-45.056-45.056 0-24.576 20.48-45.056 45.056-45.056H593.92c24.576 0 45.056 20.48 45.056 45.056 2.048 24.576-18.432 45.056-45.056 45.056z" fill="#707a8a" p-id="29650"></path><path d="M466.944 407.552v-165.888c0-24.576 20.48-45.056 45.056-45.056 24.576 0 45.056 20.48 45.056 45.056v165.888c0 24.576-20.48 45.056-45.056 45.056-24.576 2.048-45.056-18.432-45.056-45.056z" fill="#707a8a" p-id="29651"></path></svg>
							<span class="fe6im f16">&nbsp;&nbsp;<?php echo L('我的邀请');?></span>
						</div>
							<div class="lbox_r">
								<i class="bi bi-chevron-right fcc fw" style="font-size: 16px;"></i>
							</div>
					</div>
				</a>

				<a href="<?php echo U('Index/aboutus');?>">
				<div class="lbox  lbox-j">
					<div class="lbox_l">
						<svg t="1654074147523" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="23562" width="23" ><path d="M512 64c247.424 0 448 200.576 448 448s-200.576 448-448 448S64 759.424 64 512 264.576 64 512 64z m0 85.333333C311.701333 149.333333 149.333333 311.701333 149.333333 512s162.368 362.666667 362.666667 362.666667 362.666667-162.368 362.666667-362.666667S712.298667 149.333333 512 149.333333z m21.333333 277.333334a21.333333 21.333333 0 0 1 21.333334 21.333333v298.666667a21.333333 21.333333 0 0 1-21.333334 21.333333h-42.666666a21.333333 21.333333 0 0 1-21.333334-21.333333V448a21.333333 21.333333 0 0 1 21.333334-21.333333h42.666666z m0-170.666667a21.333333 21.333333 0 0 1 21.333334 21.333333v42.666667a21.333333 21.333333 0 0 1-21.333334 21.333333h-42.666666a21.333333 21.333333 0 0 1-21.333334-21.333333v-42.666667a21.333333 21.333333 0 0 1 21.333334-21.333333h42.666666z" fill="#707a8a" p-id="23563"></path></svg>

						<span class="fe6im f16">&nbsp;&nbsp;<?php echo L('关于我们');?></span>
					</div>

						<div class="lbox_r">
							<i class="bi bi-chevron-right fcc fw" style="font-size: 16px;"></i>
						</div>

				</div>
				</a>

				<a href="<?php echo U('Index/msb');?>">
				<div class="lbox">
					<div class="lbox_l">
						<svg t="1654074271500"  viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="27213" width="22" ><path d="M511.5 81c22.108 0 44.217 8.434 61.08 25.3l77.944 77.947h107.59c47.71 0 86.38 38.674 86.38 86.38v107.59l74.204 74.204c33.736 33.733 33.736 88.426 0 122.16l-74.205 74.205v111.329c0 47.706-38.67 86.38-86.38 86.38H646.786l-74.205 74.204C555.716 937.566 533.608 946 511.5 946c-22.11 0-44.218-8.434-61.081-25.3l-74.205-74.205H268.626c-47.704 0-86.38-38.674-86.38-86.38V652.528l-77.944-77.947c-33.736-33.733-33.736-88.426 0-122.16l77.943-77.947V270.628c0-47.705 38.677-86.38 86.38-86.38h103.85l77.943-77.947C467.283 89.435 489.39 81 511.498 81z m160.077 343.217c-11.677-11.677-30.608-11.677-42.285 0L486.564 566.944l-61.54-59.428c-11.76-11.357-30.43-11.147-41.932 0.385l-0.347 0.353-0.01 0.01c-11.47 11.878-11.14 30.807 0.739 42.278l81.098 78.316a29.762 29.762 0 0 0 13.637 7.533c10.422 3.505 22.393 1.106 30.696-7.197l162.682-162.682c11.677-11.677 11.677-30.609 0-42.285z" fill="#707a8a" p-id="27214"></path></svg>
						<span class="fe6im f16">&nbsp;&nbsp;<?php echo L('MSB认证');?></span>
					</div>

						<div class="lbox_r">
							<i class="bi bi-chevron-right fcc fw" style="font-size: 16px;"></i>
						</div>

				</div>
				</a>

				<a href="<?php echo U('Login/loginout');?>">
					<div class="lbox">
						<div class="lbox_l fcc">
							<svg width="22" height="22" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><rect width="48" height="48" fill="white" fill-opacity="0.01"/><path d="M23.9917 6L6 6L6 42H24" stroke="#707A8A" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/><path d="M33 33L42 24L33 15" stroke="#555555" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/><path d="M16 23.9917H42" stroke="#555555" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/></svg>
							<span class="fe6im f16">&nbsp;&nbsp;<?php echo L('退出');?></span>
						</div>

						<div class="lbox_r">
							<i class="bi bi-chevron-right fcc fw" style="font-size: 16px;"></i>
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
	function msg(){
		layer.msg("<?php echo L('待完善');?>");return false;
	}
</script>


<script type="text/javascript">
    function copyUrl(){
        var url_txt=$("#invit").val();
        copy(url_txt);
    }

    function copy(message) {
        var input = document.createElement("input");
        input.value = message;
        document.body.appendChild(input);
        input.select();
        input.setSelectionRange(0, input.value.length), document.execCommand('Copy');
        document.body.removeChild(input);
        layer.msg("<?php echo L('复制成功');?>");
    }
</script>
<script type="text/javascript">
    function goindex(){
        window.location.href="<?php echo U('Index/index');?>";
    }
</script>
</html>