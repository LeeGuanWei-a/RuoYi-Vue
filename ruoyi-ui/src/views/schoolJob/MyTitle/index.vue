<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="教学班编号" prop="classCode">
        <el-input
          v-model="queryParams.classCode"
          placeholder="请输入教学班编号"
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
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.startTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择结束时间">
        </el-date-picker>
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
          v-hasPermi="['schoolJob:MyTitle:add']"
        >新增</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['schoolJob:MyTitle:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['schoolJob:MyTitle:remove']"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['schoolJob:MyTitle:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="MyTitleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="题目序号" v-if="false" align="center" prop="titleId" />
      <el-table-column label="教学班编号" align="center" prop="classCode" />
      <el-table-column label="题目名称" align="center" prop="titleName" />
      <el-table-column label="描述" align="center" prop="description" />
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
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
<!--      <el-table-column label="教学班Id" align="center" prop="classId" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"

          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
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

    <!-- 添加或修改MyTitle对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="教学班" prop="className">
          <el-select v-model="form.classId" filterable clearable placeholder="请选择教学班">
            <el-option
              v-for="item in classList"
              :key="item.classId"
              :label="item.classCode"
              :value="item.classId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="题目名称" prop="titleName">
          <el-input v-model="form.titleName" placeholder="请输入题目名称" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable size="small"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable size="small"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间">
          </el-date-picker>
        </el-form-item>
<!--        <el-form-item label="文件路径" prop="upload">-->
<!--          <el-input v-model="form.upload" placeholder="请输入文件路径" />-->
<!--        </el-form-item>-->
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
<!--        <el-form-item label="教学班Id" prop="classId">-->
<!--          <el-input v-model="form.classId" placeholder="请输入教学班Id" />-->
<!--        </el-form-item>-->
<!--        <el-divider content-position="center">教学班信息</el-divider>-->
<!--        <el-row :gutter="10" class="mb8">-->
<!--          <el-col :span="1.5">-->
<!--            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddMyClass">添加</el-button>-->
<!--          </el-col>-->
<!--          <el-col :span="1.5">-->
<!--            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteMyClass">删除</el-button>-->
<!--          </el-col>-->
<!--        </el-row>-->
<!--        <el-table :data="myClassList" :row-class-name="rowMyClassIndex" @selection-change="handleMyClassSelectionChange" ref="myClass">-->
<!--          <el-table-column type="selection" width="50" align="center" />-->
<!--          <el-table-column label="序号" align="center" prop="index" width="50"/>-->
<!--          <el-table-column label="教学班名称" prop="className">-->
<!--            <template slot-scope="scope">-->
<!--              <el-input v-model="scope.row.className" placeholder="请输入教学班名称" />-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="教师Id" prop="userId">-->
<!--            <template slot-scope="scope">-->
<!--              <el-input v-model="scope.row.userId" placeholder="请输入教师Id" />-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--        </el-table>-->
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
import { listMyTitle, getMyTitle, delMyTitle, addMyTitle, updateMyTitle, listMyClass} from "@/api/schoolJob/MyTitle";
import {addInfo, getInfo} from "@/api/schoolJob/info";

export default {
  name: "MyTitle",
  data() {
    return {
      //教学班列表
      classList:[],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedMyClass: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // MyTitle表格数据
      MyTitleList: [],
      // MyClass表格数据
      myClassList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        titleName: null,
        startTime: null,
        endTime: null,
        classCode: null
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
        fileList: [],
      },
      userId: '',
      fileName: '',
      filePath: '',
    };
  },
  created() {
    this.getList();
    this.listMyClass();
    this.userId = this.$store.state.user.userId;
  },
  methods: {
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
    /** 查询MyTitle列表 */
    getList() {
      this.loading = true;
      listMyTitle(this.queryParams).then(response => {
        //console.log(JSON.stringify(response))
        this.MyTitleList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        titleId: null,
        titleName: null,
        description: null,
        startTime: null,
        endTime: null,
        upload: null,
        classId: null
      };
      this.myClassList = [];
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
      this.ids = selection.map(item => item.titleId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    // 查询教学班
    listMyClass(){
      listMyClass().then((res) => {
        //console.log(JSON.stringify(res))
        this.classList = res;
      })
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加题目";
      this.upload.fileList = [];
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const titleId = row.titleId || this.ids
      getMyTitle(titleId).then(response => {
        //console.log(JSON.stringify(response))
        this.form = response.data;
        this.myClassList = response.data.myClassList;
        this.open = true;
        this.title = "修改题目";
        this.upload.fileList = [{ name: this.form.fileName, url: this.form.filePath }];
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.myClassList = this.myClassList;
          if (this.form.titleId != null) {
            updateMyTitle(this.form).then(response => {
              //console.log(JSON.stringify(this.form))
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMyTitle(this.form).then(response => {
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
      const titleIds = row.titleId || this.ids;
      this.$modal.confirm('是否确认删除选择的题目').then(function() {
        return delMyTitle(titleIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** MyClass序号 */
    rowMyClassIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** MyClass添加按钮操作 */
    handleAddMyClass() {
      let obj = {};
      obj.className = "";
      obj.userId = "";
      this.myClassList.push(obj);
    },
    /** MyClass删除按钮操作 */
    handleDeleteMyClass() {
      if (this.checkedMyClass.length == 0) {
        this.$modal.msgError("请先选择要删除题目");
      } else {
        const myClassList = this.myClassList;
        const checkedMyClass = this.checkedMyClass;
        this.myClassList = myClassList.filter(function(item) {
          return checkedMyClass.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleMyClassSelectionChange(selection) {
      this.checkedMyClass = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('schoolJob/MyTitle/export', {
        ...this.queryParams
      }, `MyTitle_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
