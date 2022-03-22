<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="作品名称" prop="workName">
        <el-input
          v-model="queryParams.workName"
          placeholder="请输入作品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学生名称" prop="username">
        <el-input
          v-model="queryParams.username"
          placeholder="请输入学生名称"
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
      <el-form-item label="作品分数" prop="score">
        <el-input
          v-model="queryParams.score"
          placeholder="请输入作品分数"
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

    <el-table v-loading="loading" :data="MyWorkList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="作品Id" align="center" prop="workId" />
      <el-table-column label="作品名称" align="center" prop="workName">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_work" :value="scope.row.workName"/>
        </template>
      </el-table-column>
      <el-table-column label="学生Id" align="center" prop="userId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_work" :value="scope.row.userId"/>
        </template>
      </el-table-column>
      <el-table-column label="题目Id" align="center" prop="titleId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_work" :value="scope.row.titleId"/>
        </template>
      </el-table-column>
      <el-table-column label="作品分数" align="center" prop="score">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_work" :value="scope.row.score"/>
        </template>
      </el-table-column>
      <el-table-column label="文件上传" align="center" prop="upload">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_work" :value="scope.row.upload"/>
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
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="作品名称" prop="workName">
          <el-input v-model="form.workName" placeholder="请输入作品名称" />
        </el-form-item>
        <el-form-item label="学生Id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入学生Id" />
        </el-form-item>
        <el-form-item label="题目Id" prop="titleId">
          <el-input v-model="form.titleId" placeholder="请输入题目Id" />
        </el-form-item>
        <el-form-item label="作品分数" prop="score">
          <el-input v-model="form.score" placeholder="请输入作品分数" />
        </el-form-item>
        <el-form-item label="文件上传" prop="upload">
          <el-input v-model="form.upload" placeholder="请输入文件上传" />
        </el-form-item>
        <el-divider content-position="center">MyTitle信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddMyTitle">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteMyTitle">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="myTitleList" :row-class-name="rowMyTitleIndex" @selection-change="handleMyTitleSelectionChange" ref="myTitle">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="题目名称" prop="titleName">
            <template slot-scope="scope">
              <el-input v-model="scope.row.titleName" placeholder="请输入题目名称" />
            </template>
          </el-table-column>
          <el-table-column label="描述" prop="description">
            <template slot-scope="scope">
              <el-input v-model="scope.row.description" placeholder="请输入描述" />
            </template>
          </el-table-column>
          <el-table-column label="开始时间" prop="startTime">
            <template slot-scope="scope">
              <el-input v-model="scope.row.startTime" placeholder="请输入开始时间" />
            </template>
          </el-table-column>
          <el-table-column label="结束时间" prop="endTime">
            <template slot-scope="scope">
              <el-input v-model="scope.row.endTime" placeholder="请输入结束时间" />
            </template>
          </el-table-column>
          <el-table-column label="文件路径" prop="upload">
            <template slot-scope="scope">
              <el-input v-model="scope.row.upload" placeholder="请输入文件路径" />
            </template>
          </el-table-column>
          <el-table-column label="教学班Id" prop="classId">
            <template slot-scope="scope">
              <el-input v-model="scope.row.classId" placeholder="请输入教学班Id" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMyWork, getMyWork, delMyWork, addMyWork, updateMyWork } from "@/api/schoolJob/MyWork";

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
        userId: null,
        titleId: null,
        score: null,
        upload: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询MyWork列表 */
    getList() {
      this.loading = true;
      listMyWork(this.queryParams).then(response => {
        this.MyWorkList = response.rows;
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
      this.title = "添加MyWork";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const workId = row.workId || this.ids
      getMyWork(workId).then(response => {
        this.form = response.data;
        this.myTitleList = response.data.myTitleList;
        this.open = true;
        this.title = "修改MyWork";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.myTitleList = this.myTitleList;
          if (this.form.workId != null) {
            updateMyWork(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMyWork(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const workIds = row.workId || this.ids;
      this.$modal.confirm('是否确认删除MyWork编号为"' + workIds + '"的数据项？').then(function() {
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
        this.$modal.msgError("请先选择要删除的MyTitle数据");
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
