$(function () {
	$('#userForm').bootstrapValidator({
		message: '请输入正确的数据！',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	oldPwd: { //旧密码
                validators: {
                	stringLength: {
                        min: 6,
                        message: '长度不能小于6位！'
                    },
                }
            },
            
            newPwd: { //新密码
                validators: {
                	notEmpty: {
                        message: '新密码不能为空'
                    },
                	stringLength: {
                        min: 8,
                        message: '长度不能小于6位！'
                    },
                    
                    regexp: {
                        //regexp: /^[a-zA-Z0-9_\.]+$/,
                    	regexp: /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}/,
                        message: '密码中必须包含字母、数字、特殊字符，至少8个字符，最多30个字符'
                    }
                }
            },
            confirmNewPwd: { //确认新密码
            	validators: {
            		notEmpty: {
                        message: '确认密码不能为空'
                    },
            		stringLength: {
            			min: 6,
            			message: '长度不能小于6位！'
            		},
            		identical: {//相同
                        field: 'newPwd', //需要进行比较的input name值
                        message: '两次密码不一致'
                    },
            		regexp: {
            			regexp: /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}/,
                        message: '密码中必须包含字母、数字、特殊字符，至少8个字符，最多30个字符'
                    }
            	}
            },
            roleId: {
                validators: {
                    notEmpty: {
                        message: '请选择角色！'
                    }
                }
            },
            userId: {
	            validators: {
	                notEmpty: {
	                    message: '请输入用户编码！'
	                }
	            }
        	},
        	userName: {
	            validators: {
	                notEmpty: {
	                    message: '请输入用户名称！'
	                }
	            }
        	},
        	/*userPwd: {
	            validators: {
	                notEmpty: {
	                    message: '请输入密码！'
	                },
	                regexp: {
                        //regexp: /^[a-zA-Z0-9_\.]+$/,
                    	regexp: /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}/,
                        message: '密码中必须包含字母、数字、特殊字符，至少8个字符，最多30个字符'
                    }
	            }
        	},
        	userEmail: {
                validators: {
                	notEmpty: {
                        message: '请输入邮箱！'
                    },
                    emailAddress: {
                        message: '请输入正确的邮箱！'
                    }
                }
            },
            userStatus: {
                validators: {
                	notEmpty: {
                        message: '请选择状态！'
                    },
                }
            },
            userTelephone: {
                validators: {
                	notEmpty: {
                        message: '请输入电话号码！'
                    },
                    regexp: {
                        regexp: /^1[3|4|5|7|8]{1}[0-9]{9}$/,
                        message: '请输入正确的手机号码'
                    }
                }
            },
            */
        },
	}).on("success.form.bv",function(e){
		alert("33");
    	back.common.commit('userForm', '/user/addUser.do', '/user/showUserList.do');
	});
  });