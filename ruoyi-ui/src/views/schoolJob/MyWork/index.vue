<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="教学班" prop="classCode">
        <el-input
          v-model="queryParams.classCode"
          placeholder="请输入教学班"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="题目名称" prop="titleName">
        <el-input
          v-model="queryParams.titleName"
          placeholder="请输入题目名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作品名称" prop="workName">
        <el-input
          v-model="queryParams.workName"
          placeholder="请输入作品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学生名称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入学生名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['schoolJob:MyWork:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['schoolJob:MyWork:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['schoolJob:MyWork:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['schoolJob:MyWork:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

<!--    列表-->
    <el-table v-loading="loading" :data="MyWorkList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="作品序号" v-if="false" align="center" prop="workId" />
      <el-table-column label="教学班" align="center" prop="classCode"/>
      <el-table-column label="题目名称" align="center" prop="titleName"/>
      <el-table-column label="作品名称" align="center" prop="workName"/>
      <el-table-column label="学生名称" align="center" prop="nickName"/>
      <el-table-column label="作品分数" align="center" prop="score"/>
      <el-table-column label="文件下载" align="center" prop="fileId">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleDownload(scope.row)"
          >下载</el-button>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['schoolJob:MyWork:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['schoolJob:MyWork:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改MyWork对话框 -->
    <el-dialog title="作品信息" :visible.sync="open" width="900px" append-to-body>
      <div class="block" style="margin-left:10px; margin-bottom: 25px">
        <span class="demonstration" style="font-weight: bolder;margin-right: 15px">题目名称</span>
        <el-cascader
          v-model="form.titleId"
          :options="options"
          :props="{value:'id', label: 'name', children: 'children'}"
          clearable
          filterable
          @change="handleChange"/>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="作品名称" prop="workName">
          <el-input v-model="form.workName" placeholder="请输入作品名称" />
        </el-form-item>
        <el-form-item label="文件上传" prop="fileId">
          <el-upload
            ref="upload"
            :limit="1"
            :action="upload.url"
            :headers="upload.headers"
            :file-list="upload.fileList"
            :on-progress="handleFileUploadProgress"
            :on-success="handleFileSuccess"
            :auto-upload="false">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" :loading="upload.isUploading" @click="submitUpload">上传到服务器</el-button>

          </el-upload>
        </el-form-item>
        <el-form-item label="作品分数" prop="score">
          <el-input v-model="form.score" placeholder="请输入作品分数" />
        </el-form-item>
       </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import { listMyWork, getMyWork, delMyWork, addMyWork, updateMyWork, selectTitle} from "@/api/schoolJob/MyWork";
import {addInfo, getInfo} from "@/api/schoolJob/info";

export default {
  name: "MyWork",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedMyTitle: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // MyWork表格数据
      MyWorkList: [],
      // MyTitle表格数据
      myTitleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workName: null,
        classCode: null,
        nickName: null,
        titleName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 上传参数
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/common/upload",
        // 上传的文件列表
        fileList: []
      },
      options: [],
      userId: '',
      fileName: '',
      filePath: '',
    };
  },
  created() {
    this.getList();
    this.selectTitle();
    this.userId = this.$store.state.user.userId;
  },
  methods: {
    //  选择题目
    handleChange(value) {
      //console.log(JSON.stringify(value));
    },
    // 文件下载处理
    handleDownload(row) {
      let fileId = row.fileId;
      getInfo(fileId).then((res) => {
        //console.log(JSON.stringify(res))
        if (res !== null){
          const name = res.fileName;
          const url = res.filePath;
          // const suffix = url.substring(url.lastIndexOf("."), url.length);
          const a = document.createElement('a')
          // a.setAttribute('download', name + suffix)
          a.setAttribute('download', name)
          a.setAttribute('target', '_blank')
          a.setAttribute('href', url)
          a.click()
        }
      })
    },
    /** 查询MyWork列表 */
    getList() {
      this.loading = true;
      listMyWork(this.queryParams).then(res => {
        //console.log(JSON.stringify(res))
        this.MyWorkList = res.rows;
        this.total = res.total;
        this.loading = false;
      });
    },
    async selectTitle(){
      let that = this;
      selectTitle().then((res) => {
        //console.log(JSON.stringify(res))
        if (res !== null){
          that.options = res;
        }
      }).catch((err) => {
        console.log(err);
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        workId: null,
        workName: null,
        userId: null,
        titleId: null,
        score: null,
        upload: null
      };
      this.myTitleList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.workId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加作品";
      this.upload.fileList = [];
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const workId = row.workId || this.ids
      getMyWork(workId).then(response => {
        this.form = response.data;
        this.myTitleList = response.data.myTitleList;
        this.open = true;
        this.title = "修改作品";
        this.upload.fileList = [{ name: this.form.fileName, url: this.form.filePath }];
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.workId != null) {
            this.form.myTitleList = this.myTitleList;
            this.form.titleId = this.form.titleId[1];
            //console.log(JSON.stringify(this.form.titleId))
            updateMyWork(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.myTitleList = this.myTitleList;
            this.form.userId = this.userId;
            this.form.titleId = this.form.titleId[1];
            //console.log(JSON.stringify(this.form.titleId))
            addMyWork(this.form).then(response => {
              //console.log(JSON.stringify(this.form))
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    // 文件提交处理
    submitUpload() {
      this.$refs.upload.submit();
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.isUploading = false;
      this.fileName = file.name;
      this.filePath = response.url;
      // this.msgSuccess(response.msg);
      const data = {
        fileName : this.fileName,
        filePath : this.filePath
      }
      addInfo(data).then((res) => {
        //console.log(JSON.stringify(res))
        this.form.fileId = res;
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const workIds = row.workId || this.ids;
      this.$modal.confirm('是否确认删除选择的作品').then(function() {
        return delMyWork(workIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** MyTitle序号 */
    rowMyTitleIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** MyTitle添加按钮操作 */
    handleAddMyTitle() {
      let obj = {};
      obj.titleName = "";
      obj.description = "";
      obj.startTime = "";
      obj.endTime = "";
      obj.upload = "";
      obj.classId = "";
      this.myTitleList.push(obj);
    },
    /** MyTitle删除按钮操作 */
    handleDeleteMyTitle() {
      if (this.checkedMyTitle.length == 0) {
        this.$modal.msgError("请先选择要删除作品");
      } else {
        const myTitleList = this.myTitleList;
        const checkedMyTitle = this.checkedMyTitle;
        this.myTitleList = myTitleList.filter(function(item) {
          return checkedMyTitle.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleMyTitleSelectionChange(selection) {
      this.checkedMyTitle = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('schoolJob/MyWork/export', {
        ...this.queryParams
      }, `MyWork_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
