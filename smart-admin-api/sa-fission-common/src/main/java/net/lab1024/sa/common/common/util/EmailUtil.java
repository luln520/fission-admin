package net.lab1024.sa.common.common.util;

/**
 * 邮箱发送工具
 */
public class EmailUtil {

    /**
     *邮箱发送工具  表 config
     * 参数： phone content（内容）
     * 大致逻辑：
     *      1.读取 config 表的四个字段  smsemail,emailcode,smtpdz,smstemple    where id =1  得到发送邮箱的配置信息
     *      2.取出来的四个字段都去除 前后空格
     *      3.邮箱   pncexvip@gmail.com   是谷歌邮箱
     *      2.拼接参数  host=$config['smtpdz']，
     *                  Username = $smsemail，
     *                  Password = $emailcode，
     *                  SMTPSecure = "ssl"
     *                  Port = 465
     *                  CharSet = 'UTF-8'
     *                  目标邮箱
     *                  Subject = 'Verification Code' 标题
     *                  Body = $smstemple . ":" . $desc_content; 内容
     *      4,.发送请求
     *      5.读取返回并判断是否成功
     *
     * 逻辑代如下：
     *          $config = $clist = M("config")->where(array('id' => 1))->field("smsemail,emailcode,smtpdz,smstemple")->find(); （逻辑 1）
     *         $smsemail = trim($config['smsemail']);  （逻辑 2）
     *         $emailcode = trim($config['emailcode']);
     *         $smstemple = trim($config['smstemple']);
     *         $mail = new \PHPMailer(); （逻辑 3）
     *         $mail->SMTPDebug = 0;
     *         $mail->isSMTP();
     *         $mail->Host = trim($config['smtpdz']);
     *         $mail->SMTPAuth = true;
     *         $mail->Username = $smsemail;
     *         $mail->Password = $emailcode;
     *         $mail->SMTPSecure = "ssl";
     *         $mail->Port = 465;
     *         $mail->CharSet = 'UTF-8';
     *         $mail->setFrom($smsemail, "Verification Code");
     *         $mail->addAddress($toemail, '');
     *         $mail->addReplyTo($smsemail, "Reply");
     *         $mail->Subject = L('Verification Code');
     *         $mail->Body = $smstemple . ":" . $desc_content;
     *         if (!$mail->send()) { （逻辑 4 5）
     *             return 0;
     *         } else {
     *             return 1;
     *         }
     *
     */

}
