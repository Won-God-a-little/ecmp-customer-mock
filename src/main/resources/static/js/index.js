
const instance = axios.create({
    defaultDomain,
    timeout: 5000
})

// 请求拦截器
instance.interceptors.request.use(
    (config) => {
        const token = sessionStorage.getItem('authorization')
        if (token) {
            config.headers.Authorization = token
        }
        return config
    },
    (err) => Promise.reject(err)
)

// 响应拦截器
instance.interceptors.response.use(
    (res) => {
        if (res.data.code === '200') {
            console.log('响应成功')
            return Promise.resolve(res.data.obj)
        } else {
            console.log('响应失败')
            Vue.prototype.$message({message: res.data.msg, type: 'error'})
            return Promise.reject(res.data)
        }
    },
    (err) => {
        if (err.response?.status === 500) {
            console.log('响应失败')
            Vue.prototype.$message({message: '500服务异常! ', type: 'error'})
            return Promise.reject(err)
        }
    }
)
new Vue({
    el: '#app',
    created() {
        console.log('页面组件创建中...')
        this.getInvokeList() // 调用列表
        this.getIntegrationList() // 集成列表
        this.getCallbackList() // 回调列表
    },
    data: {
        activeName: 'api',
        tabQueue: ['tab-api'],
// 1. 模拟调用 数据区
        invoke_input: '',
        apiList: [{refreshTime: ''}],
        isHasApi: false,
        req_type: '',
        req_url: '',
        paramsData: [],
        reqParams: [],
        req_result: '',

// 2. 模拟集成 数据区
        integration_input: '',
        iframe_url: '',
        integrationList: [],
// 3. 模拟回调 数据
        callback_input: '',
        corp_input: '',
        callbackList: [], // 回调列表
        callback_url: '',
        tableData: [],
        isAuto: false,
        selectedRow: {},
        isCallback: false,
// 4. 企业配置 数据区
        settingForm: {
            code: '',
            name: '',
            logoUrl: '',
            openId: '',
            server: '',
            secretId: '',
            secretKey: '',
            signatureKey: '',
            encodingKey: ''
        },

        settingFormRules: {
            code: [
                { required: true, message: '请输入企业代码', trigger: 'blur' },
            ],
            name: [
                { required: true, message: '请输入企业名称', trigger: 'blur' },
            ],
            logoUrl: [
                { required: true, message: '请输入企业LOGO完整URL', trigger: 'blur' },
            ],
            openId: [
                { required: true, message: '请输入openId', trigger: 'blur' },
            ],
            secretId: [
                { required: true, message: '请输入密钥Id', trigger: 'blur' },
            ],
            server: [
                { required: true, message: '请输入服务器地址', trigger: 'blur' },
            ],
            secretKey: [
                { required: true, message: '请输入企业密钥', trigger: 'blur' },
            ],
            signatureKey: [
                { required: true, message: '请输入signatureKey', trigger: 'blur' },
            ],
            encodingKey: [
                { required: true, message: '请输入encodingKey', trigger: 'blur' },
            ],
        }
    },

    methods: {
        <!-- 通用 方法区 -->
        handleClick(tab) {
            const tabName = "tab-" + tab.name
            this.tabQueue.push(tabName); // 当前tab加入队列

            /* 保证队列第一个永远是 上次的tab */
            if (this.tabQueue.length > 2)
                this.tabQueue.shift(); // 队列长度大于2时，删除第一个
        },

        searchName(name, type) {
            if (type === 1) {
                this.getInvokeList(name)
            } else if (type === 2) {
                this.getIntegrationList(name)
            } else if (type === 3) {
                this.getCallbackList(name)
            }
        },

        <!-- 1. 模拟调用 方法区 -->
        async getInvokeList(key) {
            console.log('获取接口列表...')
            const url = `${defaultDomain}/api/list` + (key ? '?key=' + key : '')
            this.apiList = await instance.get(url)
        },
        async getInterfaceParams(id) {
            this.req_result = ''
            console.log('获取接口信息...')
            const url = `${defaultDomain}/api/interfaceInfo?id=` + id
            const res = await instance.get(url)
            this.isHasApi = true
            this.paramsData = res
            this.reqParams = res.requestParams // 绑定请求表格
            this.req_type = res.method // 绑定请求类型
            this.req_url = res.url; // 绑定请求地址
        },

        async getReqParams(params) {
            if (!this.isHasApi) {
                this.$message({message: '请先选择接口! ', type: 'warning'})
                return
            }
            let miss = false
            this.reqParams.map((item) => {
                if (item.required==="是" && item.defaultValue === '') {
                    console.log("参数缺失")
                    this.$message({message: '请填写必填项! ', type: 'warning'})
                    miss = true;
                }
            })
            if (miss) return
            console.log('模拟 请求调用接口...')
            const res = await instance.post(`${defaultDomain}/api/mockCall`, {
                id: params.id,
                title: params.title,
                url: this.req_url,
                method: this.req_type,
                requestParams: this.reqParams // 这里要传更新后的params
            })
            this.req_result = JSON.stringify(res, null, 2);
            this.$message({message: '请求成功! ', type: 'success'})
        },

        <!-- 2. 模拟集成 方法区 -->
        async getIntegrationList(name) {
            console.log('获取集成列表...')
            const url = `${defaultDomain}/integration/list?key=` +  name
            this.integrationList = await instance.get(url)
        },
        async getAuthorization() {
            const url = `${defaultDomain}/integration/getAuthorization`
            await instance.get(url);
            this.$message({message: 'Authorization更新成功! ', type: 'success'})
        },
        displayManual() {
            this.iframe_url ="https://hc.eqxiu.cn/doc/34/"
        },

        async displayPage(api) {
            console.log('显示页面...')
            console.log(api)
            const res = await instance.post(`${defaultDomain}/integration/showPage`, api)
            console.log(res)
            this.iframe_url = res
        },


        <!-- 3. 模拟回调 方法区 -->
        async getCallbackList(key) { // 获取列表
            console.log('获取回调列表...')
            const url = `${defaultDomain}/callback/list` + (key ? '?key=' + key : '')
            this.callbackList = await instance.get(url)
        },
        async getCallbackDetail(url, code) { // 获取 表格数据
            if(code === '' || url.substring(url.length - 6) === "(企业代号)") {
                this.$message({message: '请请先创建或登录一个模拟企业! ', type: 'warning'})
                return
            }
            console.log("获取 回调信息...")
            const u = `${defaultDomain}/callback/log?url=` + url + '&&code=' + code
            this.tableData = await instance.get(u)
            const serverUrl = sessionStorage.getItem("server") + url
            navigator.clipboard.writeText(serverUrl)
            if (url) {
                this.$message({message: `地址${serverUrl} 复制成功! `, type: 'success'})
            }



        },
        showDetails(row) {
            this.isCallback = true;
            this.selectedRow = row; // 保存 选中的行数据
            this.selectedRow.requestParam = JSON.parse(row.requestParam)
            this.selectedRow.returnValue = JSON.parse(row.returnValue)

        },
        getCallbackDetailAuto(code) { // 自动刷新
            console.log(this.isAuto);
            clearInterval(this.timer); // 清除之前的定时器
            if (this.isAuto) {
                this.timer = setInterval(() => {
                    console.log('自动刷新中...');
                    this.getCallbackDetail('', code);
                }, 5000);
            }
        },


        <!-- 4. 企业配置 方法区 -->
        async handleSettingSearch(code) {// 查询
            if (!code) {
                this.$message({message: '请输入企业代码! ', type: 'warning'})
                return
            }
            console.log('企业配置 查询...')
            const url = `${defaultDomain}/corp/info?code=` + code
            const res = await instance.get(url)
            this.settingForm = res.corp
            this.corp_input = res.corp.code

            this.callbackList.map((element) => {
                if(element.subMenus) {
                    element.subMenus.map((item) => {
                        if(item.url.substring(item.url.length - 6) === "(企业代号)")
                            item.url = item.url.slice(0, -6) + res.corp.code
                    })
                }else {
                    if(element.url.substring(element.url.length - 6) === "(企业代号)")
                        element.url = element.url.slice(0, -6) + res.corp.code
                }
            })
            sessionStorage.setItem("authorization", res.authorization)
            sessionStorage.setItem("server", res.corp.server)
        },
        handleSettingSave() {// 保存
            console.log('企业配置 保存...')
            console.log(this.settingForm)
            this.$refs.settingForm.validate((valid) => {
                if (valid) {
                    instance.post(`${defaultDomain}/corp/save`, this.settingForm)
                    this.$message({message: '保存成功! ', type: 'success'})
                } else {
                    // 表单校验不通过，显示错误提示
                    this.$message.error('请填写完整的表单信息');
                }
            });
        },
    }
})
