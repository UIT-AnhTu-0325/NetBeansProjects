import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import axios from "axios";
import { TextField, FormControl, Button } from "@mui/material";

const baseURL = "http://localhost:8901/subjects";

export const ListSubject = () => {
  const [subjects, setSubjects] = useState([]);

  const [id, setId] = useState("");
  const [name, setName] = useState("");
  const [nums, setNums] = useState(0);

  const columns = [
    { field: "id", headerName: "ID", width: 70 },
    { field: "firstName", headerName: "First name", width: 130 },
    { field: "lastName", headerName: "Last name", width: 130 },
    {
      field: "age",
      headerName: "Age",
      type: "number",
      width: 90,
    },
    {
      field: "fullName",
      headerName: "Full name",
      description: "This column has a value getter and is not sortable.",
      sortable: false,
      width: 160,
      valueGetter: (params) =>
        `${params.row.firstName || ""} ${params.row.lastName || ""}`,
    },
  ];

  useEffect(() => {
    // axios.get(baseURL).then((response) => {
    //   setSubjects(response.data);
    // });

    //Temp

    const rows = [
      { id: 1, lastName: "Snow", firstName: "Jon", age: 35 },
      { id: 2, lastName: "Lannister", firstName: "Cersei", age: 42 },
      { id: 3, lastName: "Lannister", firstName: "Jaime", age: 45 },
      { id: 4, lastName: "Stark", firstName: "Arya", age: 16 },
      { id: 5, lastName: "Targaryen", firstName: "Daenerys", age: null },
      { id: 6, lastName: "Melisandre", firstName: null, age: 150 },
      { id: 7, lastName: "Clifford", firstName: "Ferrara", age: 44 },
      { id: 8, lastName: "Frances", firstName: "Rossini", age: 36 },
      { id: 9, lastName: "Roxie", firstName: "Harvey", age: 65 },
    ];

    setSubjects(rows);
  }, []);

  const handleSubmit = (event) => {
    event.preventDefault();
    //submit form
  };

  const handleAddSubject = () => {
    setId("");
    setName("");
    setNums(0);
  };

  const handleRowClick = (e) => {
    console.log(e)
  }

  if (!subjects || subjects.length === 0) return <div>Loading...</div>;

  return (
    <div>
      <div style={{ margin: 30 }}>
        <h2>List subjects</h2>
      </div>
      <div style={{ display: "flex" }}>
        <div style={{ width: 900, margin: 30 }}>
          <DataGrid
            rows={subjects}
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
              onClick={handleAddSubject}
            >
              Thêm
            </Button>
            <Button
              variant="outlined"
              color="secondary"
              type="submit"
              style={{ marginRight: 20 }}
            >
              Sứa
            </Button>
            <Button variant="outlined" color="secondary" type="submit">
              Xóa
            </Button>
          </div>

          <form autoComplete="off" onSubmit={handleSubmit}>
            <TextField
              label="Mã môn học"
              onChange={(e) => setId(e.target.value)}
              required
              variant="outlined"
              color="secondary"
              sx={{ mb: 3 }}
              fullWidth
              value={id}
            />
            <TextField
              label="Tên môn học"
              onChange={(e) => setName(e.target.value)}
              required
              variant="outlined"
              color="secondary"
              value={name}
              fullWidth
              sx={{ mb: 3 }}
            />

            <TextField
              label="Số tín chỉ"
              onChange={(e) => setNums(e.target.value)}
              required
              variant="outlined"
              color="secondary"
              value={nums}
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
