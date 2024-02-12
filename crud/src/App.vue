<script>
import Card from '../src/components/Card.vue';

export default {
  components: {
    Card
  },
  data() {
    return {
      cardData: [],
      loading: true,
      nombre: '',
      autor: '',
      genero: '',
      anio_publicacion: '',
      nombreState: null,
      autorState: null,
      generoState: null,
      anio_publicacionState: null,

      idEdit: '',
      nombreEdit: '',
      autorEdit: '',
      generoEdit: '',
      anio_publicacionEdit: '',
      nombreStateEdit: null,
      autorStateEdit: null,
      generoStateEdit: null,
      anio_publicacionStateEdit: null,

      idDelete: ''

    };
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    async fetchData() {
      try {

        this.loading = true

        const options = {
          method: 'GET',
          headers: { 'Content-Type': 'application/json', 'User-Agent': 'insomnia/8.5.1' },
        };

        const response = await fetch('http://127.0.0.1:8080/api-crudvueback/libro/', options)
          .then(response => response.json())
          .then(response => {
            return response;
          })
          .catch(err => console.error(err));


        const jsonData = response.data;
        console.log(jsonData);
        this.cardData = jsonData;
        this.loading = false
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },
    checkFormValidity() {
      const valid = this.$refs.form.checkValidity()
      this.nombreState = valid
      this.autorState = valid
      this.generoState = valid
      this.anio_publicacionState = valid
      return valid
    },
    resetModal() {
      this.nombre = ''
      this.nombreState = null
      this.autor = ''
      this.autorState = null
      this.genero = ''
      this.generoState = null
      this.anio_publicacion = ''
      this.anio_publicacionState = null
    },
    async handleSubmit() {
      // Exit when the form isn't valid
      if (!this.checkFormValidity()) {
        return
      }

      const options = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'User-Agent': 'insomnia/8.5.1' },
        body: JSON.stringify({
          nombre: this.nombre,
          autor: this.autor,
          genero: this.genero,
          anio_publicacion: this.anio_publicacion
        })
      };

      const fetchPost = await fetch('http://localhost:8080/api-crudvueback/libro/', options)
        .then(response => response.json())
        .then(response => {
          return response;
        })
        .catch(err => console.error(err));

      const response = fetchPost;
      if (response.status === 200) {
        this.fetchData();
        this.hideModal();
        alert('Libro añadido correctamente');
      } else {
        alert('Error al añadir el libro');
        this.hideModal();
      }

      // Hide the modal manually
      this.$nextTick(() => {
        this.$bvModal.hide('modal-prevent-closing')
      })
    },
    hideModal() {
      this.$refs['modalAdd'].hide()
    },




    checkFormValidityEdit() {
      const valid = this.$refs.form.checkValidity()
      this.nombreStateEdit = valid
      this.autorStateEdit = valid
      this.generoStateEdit = valid
      this.anio_publicacionStateEdit = valid
      return valid
    },
    resetModalEdit() {
      this, this.idEdit = this.data.id
      this.nombreEdit = this.data.nombre
      this.autorEdit = this.data.autor
      this.generoEdit = this.data.genero
      this.anio_publicacionEdit = this.data.anio_publicacion
      this.nombreStateEdit = true
      this.autorStateEdit = true
      this.generoStateEdit = true
      this.anio_publicacionStateEdit = true
    },
    hideModalEdit() {
      this.$refs.modalEdit.hide()
    },
    async handleSubmitEdit() {
      if (!this.checkFormValidity()) {
        return
      }

      const options = {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json', 'User-Agent': 'insomnia/8.5.1' },
        body: JSON.stringify({
          id: this.idEdit,
          nombre: this.nombreEdit,
          autor: this.autorEdit,
          genero: this.generoEdit,
          anio_publicacion: this.anio_publicacionEdit
        })
      };

      const fetchPut = await fetch('http://localhost:8080/api-crudvueback/libro/', options)
        .then(response => response.json())
        .then(response => {
          return response;
        })
        .catch(err => console.error(err));

      const response = fetchPut;
      if (response.status === 200) {
        this.fetchData();
        alert('Libro editado correctamente');
      } else {
        alert('Error al editar el libro');
      }


      this.hideModalEdit()
    },
    openEditModal(data) {
      // Asigna los datos del libro al modal de edición
      this.idEdit = data.id;
      this.nombreEdit = data.nombre;
      this.autorEdit = data.autor;
      this.generoEdit = data.genero;
      this.anio_publicacionEdit = data.anio_publicacion;
      // Mostrar el modal de edición
      this.$refs.modalEdit.show();
    },




    resetModalDelete() {
      this, this.idDelete = this.data.idDelete

    },
    hideModalDelete() {
      this.$refs.modalDelete.hide()
    },
    openDeleteModal(id) {
      // Asigna los datos del libro al modal de edición
      this.idDelete = id
      // Mostrar el modal de edición
      this.$refs.modalDelete.show();

    },
    async deleteBook() {
      const options = {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json', 'User-Agent': 'insomnia/8.5.1' },
      };

      const fetchDelete = await fetch('http://localhost:8080/api-crudvueback/libro/' + this.idDelete, options)
        .then(response => response.json())
        .then(response => {
          return response;
        })
        .catch(err => console.error(err));

      const response = fetchDelete;
      if (response.status === 200) {
        this.fetchData();
        alert('Libro eliminado correctamente');
      } else {
        alert('Error al eliminar el libro');


      }

      this.hideModalDelete()

    }
  }
}
</script>

<template>
  <div>

    <div v-if="loading" class="overlay">
      <div class="spinner"></div>
    </div>

    <h1 class="text-center" style="margin-top: 30px;">CRUD Libros</h1>
    <b-container style="margin-top:50px; display: flex; ">
      <b-row>
        <b-col md="4" sm="6" xs="12" v-for="item in cardData" :key="item.id" style="margin-bottom: 50px;">
          <Card :data="item" @edit="openEditModal" @delete="openDeleteModal" />
        </b-col>
      </b-row>
    </b-container>
    <b-button v-b-modal.modalAdd variant="dark" class="floating-button">
      <span class="plus-icon">+</span>
    </b-button>

    <b-modal id="modalAdd" ref="modalAdd" title="Añade un nuevo Libro" @show="resetModal" @hidden="resetModal" centered
      hide-footer hide-header-close>
      <form ref="form" @submit.stop.prevent="handleSubmit">
        <b-form-group label="Nombre" label-for="nombre-input" invalid-feedback="El nombre es obligatorio"
          :state="nombreState">
          <b-form-input id="nombre-input" v-model="nombre" :state="nombreState" required></b-form-input>
        </b-form-group>
        <b-form-group label="Autor" label-for="autor-input" invalid-feedback="El autor es obligatorio"
          :state="autorState">
          <b-form-input id="autor-input" v-model="autor" :state="autorState" required></b-form-input>
        </b-form-group>
        <b-form-group label="Género" label-for="genero-input" invalid-feedback="El género es obligatorio"
          :state="generoState">
          <b-form-input id="genero-input" v-model="genero" :state="generoState" required></b-form-input>
        </b-form-group>
        <b-form-group label="Año de publicación" label-for="anio_publicacion-input"
          invalid-feedback="El año de publicación es obligatorio" :state="anio_publicacionState">
          <b-form-input id="anio_publicacion-input" v-model="anio_publicacion" :state="anio_publicacionState"
            required></b-form-input>
        </b-form-group>

        <div style="display: flex; justify-content: end; margin-top: 30px;">
          <b-button variant="secondary" style="margin-right: 10px;" @click="hideModal"> Cancelar</b-button>
          <b-button type="submit" variant="primary"> Añadir</b-button>

        </div>

      </form>
    </b-modal>



    <b-modal id="modalEdit" ref="modalEdit" title="Editar libro" @show="resetModalEdit" @hidden="resetModalEdit" centered
      hide-footer hide-header-close>
      <form ref="form" @submit.stop.prevent="handleSubmitEdit">
        <b-form-group label="Nombre" label-for="nombre-input-edit" invalid-feedback="El nombre es obligatorio"
          :state="nombreState">
          <b-form-input id="nombre-input-edit" v-model="nombreEdit" :state="nombreStateEdit" required></b-form-input>
        </b-form-group>
        <b-form-group label="Autor" label-for="autor-input-edit" invalid-feedback="El autor es obligatorio"
          :state="autorState">
          <b-form-input id="autor-input-edit" v-model="autorEdit" :state="autorStateEdit" required></b-form-input>
        </b-form-group>
        <b-form-group label="Género" label-for="genero-input-edit" invalid-feedback="El género es obligatorio"
          :state="generoState">
          <b-form-input id="genero-input-edit" v-model="generoEdit" :state="generoStateEdit" required></b-form-input>
        </b-form-group>
        <b-form-group label="Año de publicación" label-for="anio_publicacion-input-edit"
          invalid-feedback="El año de publicación es obligatorio">
          <b-form-input id="anio_publicacion-input-edit" v-model="anio_publicacionEdit" :state="anio_publicacionStateEdit"
            required></b-form-input>
        </b-form-group>

        <div style="display: flex; justify-content: end; margin-top: 30px;">
          <b-button variant="secondary" style="margin-right: 10px;" @click="hideModalEdit"> Cancelar</b-button>
          <b-button type="submit" variant="primary"> Editar</b-button>

        </div>

      </form>
    </b-modal>


    <b-modal id="modalDelete" ref="modalDelete" title="Eliminar libro" @show="resetModalDelete" @hidden="resetModalDelete"
      centered hide-footer hide-header-close>

      <h3>¿Seguro que deseas eliminar este libro? </h3>

      <div style="display: flex; justify-content: end; margin-top: 30px;">
        <b-button variant="secondary" style="margin-right: 10px;" @click="hideModalDelete"> Cancelar</b-button>
        <b-button variant="danger " @click="deleteBook"> Eliminar</b-button>

      </div>
    </b-modal>

  </div>
</template>

<style scoped>
.floating-button {
  position: fixed;
  bottom: 40px;
  right: 40px;
  border-radius: 50%;
  width: 60px;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.plus-icon {
  font-size: 24px;
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.7);
  z-index: 9999;
  display: flex;
  justify-content: center;
  align-items: center;
}

.spinner {
  border: 4px solid rgba(0, 0, 0, 0.1);
  border-left-color: #333;
  border-radius: 50%;
  width: 50px;
  height: 50px;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}
</style>
