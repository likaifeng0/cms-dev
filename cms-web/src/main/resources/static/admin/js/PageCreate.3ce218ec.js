(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["PageCreate"],{"0705":function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",[a("a-input",{attrs:{placeholder:"请输入标题"},model:{value:e.queryParam.title,callback:function(t){e.$set(e.queryParam,"title",t)},expression:"queryParam.title"}}),a("mavon-editor",{ref:"md",staticStyle:{"min-height":"600px","z-index":"1"},on:{change:e.change},model:{value:e.queryParam.originalContent,callback:function(t){e.$set(e.queryParam,"originalContent",t)},expression:"queryParam.originalContent"}}),a("div",{staticClass:"article-bottom"},[a("div",{staticClass:"article-option"},[a("a-button",{attrs:{type:"primary"},on:{click:e.showDrawer}},[e._v("打开发布面板")])],1)]),a("a-drawer",{attrs:{title:"创建或跟新页面",placement:"right",closable:!1,visible:e.visible,width:"30rem"},on:{close:e.onClose}},[a("a-form-item",{attrs:{label:"页面名称"}},[a("a-input",{model:{value:e.queryParam.title,callback:function(t){e.$set(e.queryParam,"title",t)},expression:"queryParam.title"}})],1),a("a-form",[a("a-form-item",{attrs:{label:"视图路径"}},[a("a-input",{attrs:{placeholder:"请输入视图路径"},model:{value:e.queryParam.viewName,callback:function(t){e.$set(e.queryParam,"viewName",t)},expression:"queryParam.viewName"}})],1),a("a-form-item",{attrs:{label:"选择模板"}},[a("a-select",{staticStyle:{width:"100%"},model:{value:e.queryParam.templateName,callback:function(t){e.$set(e.queryParam,"templateName",t)},expression:"queryParam.templateName"}},e._l(e.templates,(function(t){return a("a-select-option",{key:t.id,attrs:{value:t.enName}},[e._v(e._s(t.name))])})),1)],1),a("a-form-item",[a("a-button",{on:{click:e.submit}},[e._v("发布")])],1)],1)],1)],1)])},r=[],i=a("b2d8"),o=(a("64e1"),a("c621")),u=a("ed66"),c={components:{mavonEditor:i["mavonEditor"]},data:function(){return{queryParam:{originalContent:"",templateName:"",title:"",viewName:"",status:"PUBLISHED"},visible:!1,templates:[],isUpdate:!1}},beforeRouteEnter:function(e,t,a){var n=e.query.sheetId;a((function(e){n&&u["a"].findById(n).then((function(t){var a=t.data.data;e.queryParam.originalContent=a.originalContent,e.queryParam.title=a.title,e.queryParam.viewName=a.viewName,e.queryParam.status=a.status,e.isUpdate=!0}))}))},methods:{change:function(e,t){this.queryParam.formatContent=t},submit:function(){var e=this;this.isUpdate?u["a"].update(this.$route.query.sheetId,this.queryParam).then((function(t){e.$notification["success"]({message:"更新成功:"+t.data.message}),e.$router.push({name:"PageList"})})):u["a"].create(this.queryParam).then((function(t){e.$notification["success"]({message:"添加成功"+t.data.message}),e.$router.push({name:"PageList"})}))},showDrawer:function(){this.loadTemplate(),this.visible=!0},onClose:function(){this.visible=!1},handleChange:function(e){this.queryParam.channelId=e},loadTemplate:function(){var e=this;o["a"].findByType("SHEET").then((function(t){e.templates=t.data.data}))}}},s=c,l=(a("732d"),a("2877")),m=Object(l["a"])(s,n,r,!1,null,null,null);t["default"]=m.exports},"732d":function(e,t,a){"use strict";var n=a("eb50"),r=a.n(n);r.a},eb50:function(e,t,a){},ed66:function(e,t,a){"use strict";var n=a("9efd"),r="/api/sheet",i={list:function(e){return Object(n["a"])({url:r,params:e,method:"get"})},addOrRemoveToMenu:function(e){return Object(n["a"])({url:"".concat(r,"/addOrRemoveToMenu/").concat(e),method:"get"})},findListByChannelId:function(e){return Object(n["a"])({url:"".concat(r,"/findListByChannelId/").concat(e),method:"get"})},deleteById:function(e){return Object(n["a"])({url:"".concat(r,"/delete/").concat(e),method:"get"})},generateHtml:function(e){return Object(n["a"])({url:"".concat(r,"/generate/").concat(e),method:"get"})},query:function(e){return Object(n["a"])({url:r,params:e,method:"get"})},findById:function(e){return Object(n["a"])({url:"".concat(r,"/find/").concat(e),method:"get"})},create:function(e){return Object(n["a"])({url:r,data:e,method:"post"})},update:function(e,t){return Object(n["a"])({url:"".concat(r,"/update/").concat(e),data:t,method:"post"})}};t["a"]=i}}]);
//# sourceMappingURL=PageCreate.3ce218ec.js.map