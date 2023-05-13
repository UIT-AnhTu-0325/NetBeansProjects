import { useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import {
  TextField,
  Button,
  Select,
  MenuItem,
  InputLabel,
  FormControl,
} from "@mui/material";
import axios from "axios";

const baseURLGet = (mamh) => {
  return `http://localhost:8901/subjects/${mamh}`;
};

const baseURLGetStudent = "http://localhost:8901/students";

const baseURLAddGrade = "http://localhost:8901/subjects/addGrade";
const baseURLDeleteGrade = "http://localhost:8901/subjects/deleteGrade";

export const SubjectDetail = () => {
  let { id } = useParams();

  const [grades, setGrades] = useState([]);
  const [students, setStudents] = useState([]);
  const [subjectName, setSubjectName] = useState([]);

  const [idStudent, setIdStudent] = useState();
  const [nameStudent, setNameStudent] = useState();
  const [gradeCC, setGradeCC] = useState();
  const [gradeBTL, setGradeBTL] = useState();
  const [gradeCK, setGradeCK] = useState();

  const [formType, setFormType] = useState("Add");

  const [selectedId, setSelectedId] = useState();
  const columns = [
    { field: "MASV", headerName: "Mã sinh viên", width: 150 },
    { field: "TENSV", headerName: "Tên sinh viên", width: 150 },
    { field: "DIEMCC", headerName: "Điểm chuyên cần", width: 150 },
    { field: "DIEMBTL", headerName: "Điểm bài tập lớn", width: 150 },
    { field: "DIEMCK", headerName: "Điểm cuối kì", width: 150 },
  ];

  useEffect(() => {
    axios.get(baseURLGetStudent).then((response) => {
      const datas = response.data.students;
      const res = datas.map((x) => {
        return { ...x, id: datas.indexOf(x) };
      });
      setStudents(res);
    });
    axios.get(baseURLGet(id)).then((response) => {
      const datas = response.data.studentGrades;
      if (datas.length > 0) {
        setSubjectName(datas[0].TENMH);
      }
      const res = datas.map((x) => {
        return { ...x, id: datas.indexOf(x) };
      });
      setGrades(res);
    });
  }, [id]);

  useEffect(() => {
    if (grades.length > 0) {
      let hasGrade = grades.map((x) => x.MASV);
      let filtered = students.filter((x) => hasGrade.indexOf(x.MaSV) === -1);
      setStudents(filtered);
    }
  }, [grades]);

  const handleRowClick = (e) => {
    setSelectedId(e.row.id);
  };

  const handleDeleteGrade = (e) => {
    const deleteGrade = grades.find((x) => x.id === selectedId);
    if (window.confirm(`Are you sure to delete`) === true) {
      axios
        .post(baseURLDeleteGrade, { MASV: deleteGrade.MASV, MAMH: id })
        .then(() => {
          const newDatas = grades.filter((x) => x.MASV !== deleteGrade.MASV);
          setGrades(newDatas);
        });
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const newGrade = {
      MAMH: id,
      MASV: idStudent,
      TENSV: nameStudent,
      DIEMCC: gradeCC,
      DIEMBTL: gradeBTL,
      DIEMCK: gradeCK,
    };
    if (formType === "Add") {
      axios.post(baseURLAddGrade, newGrade).then(() => {
        let newDatas = [
          ...grades,
          {
            MASV: newGrade.MASV,
            DIEMCC: newGrade.DIEMCC,
            DIEMBTL: newGrade.DIEMBTL,
            DIEMCK: newGrade.DIEMCK,
            TENSV: newGrade.TENSV,
            id: grades.length + 1,
          },
        ];
        setGrades(newDatas);
      });
    }
  };

  const handleAddGrade = () => {
    setIdStudent();
    setGradeCC();
    setGradeBTL();
    setGradeCK();
    setFormType("Add");
  };

  const handleStudentSelectChange = (e) => {
    students.forEach((item) => {
      if (item.MaSV === e.target.value) {
        setNameStudent(item.TenSV);
        setIdStudent(item.MaSV);
      }
    });
  };

  if (!grades) return <div>Loading...</div>;

  return (
    <div>
      <div style={{ margin: 30 }}>
        <h2>
          List grade of subject {id} - {subjectName}
        </h2>
      </div>
      <div style={{ display: "flex" }}>
        <div style={{ width: 900, margin: 30 }}>
          <DataGrid
            rows={grades}
            columns={columns}
            initialState={{
              pagination: {
                paginationModel: { page: 0, pageSize: 7 },
              },
            }}
            onRowClick={handleRowClick}
          />
        </div>
        <div style={{ width: 700, margin: 30 }}>
          <div style={{ marginBottom: 30 }}>
            <Button
              variant="outlined"
              color="secondary"
              type="submit"
              style={{ marginRight: 20 }}
              onClick={handleAddGrade}
            >
              Thêm
            </Button>
            {/* <Button
              variant="outlined"
              color="secondary"
              type="submit"
              style={{ marginRight: 20 }}
              //onClick={handleEditSubject}
            >
              Sứa
            </Button> */}
            <Button
              variant="outlined"
              color="secondary"
              type="submit"
              style={{ marginRight: 30 }}
              onClick={handleDeleteGrade}
            >
              Xóa
            </Button>
          </div>

          <form autoComplete="off" onSubmit={handleSubmit}>
            <FormControl sx={{}} fullWidth>
              <InputLabel id="student-select-label">Sinh Viên</InputLabel>
              <Select
                label="Sinh Viên"
                labelId="student-select-label"
                value={idStudent}
                onChange={handleStudentSelectChange}
                color="secondary"
                fullWidth
                sx={{ mb: 3 }}
              >
                {students.map((value) => {
                  return <MenuItem value={value.MaSV}>{value.TenSV}</MenuItem>;
                })}
              </Select>
            </FormControl>
            <TextField
              label="Điểm chuyên cần"
              onChange={(e) => setGradeCC(e.target.value)}
              required
              variant="outlined"
              color="secondary"
              value={gradeCC}
              fullWidth
              sx={{ mb: 3 }}
              type="number"
            />

            <TextField
              label="Điểm bài tập lớn"
              onChange={(e) => setGradeBTL(e.target.value)}
              required
              variant="outlined"
              color="secondary"
              value={gradeBTL}
              fullWidth
              sx={{ mb: 3 }}
              type="number"
            />

            <TextField
              label="Điểm cuối kì"
              onChange={(e) => setGradeCK(e.target.value)}
              required
              variant="outlined"
              color="secondary"
              value={gradeCK}
              fullWidth
              sx={{ mb: 3 }}
              type="number"
            />

            <Button variant="outlined" color="secondary" type="submit">
              Lưu
            </Button>
          </form>
        </div>
      </div>
    </div>
  );
};
